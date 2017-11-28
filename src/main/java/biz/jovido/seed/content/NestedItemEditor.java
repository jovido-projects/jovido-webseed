package biz.jovido.seed.content;

import biz.jovido.seed.UsedInTemplates;
import biz.jovido.seed.admin.*;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class NestedItemEditor implements ItemChangeListener {

    public static class PayloadField {

        private final PayloadFieldGroup group;
        private final Payload payload;
        private NestedItemEditor nestedEditor;

        private Actions actions;
        private boolean compressed;

        public Payload getPayload() {
            return payload;
        }

        public String getId() {
            return getPayload().getUuid().toString();
        }

        public NestedItemEditor getNestedEditor() {
            return nestedEditor;
        }

        public void setNestedEditor(NestedItemEditor nestedEditor) {
            this.nestedEditor = nestedEditor;
        }

        public Actions getActions() {
            if (actions == null) {
                actions = new Actions();
            }

            return actions;
        }

        public void setActions(Actions actions) {
            this.actions = actions;
        }

        public boolean isCompressed() {
            return compressed;
        }

        public void setCompressed(boolean compressed) {
            this.compressed = compressed;
        }

        private NestedItemEditor getEditor() {
            return group.editor;
        }

        public String getCaption() {
            Payload payload = getPayload();
            String caption;
            Attribute attribute = group.getAttribute();
            if (attribute instanceof ItemAttribute) {
                Item item = ((ItemPayload) payload).getItem();
                caption = item.getStructureName();
            } else {
                PayloadGroup payloadGroup = payload.getGroup();
                caption = payloadGroup.getAttributeName();
            }

            return String.format("%s #%d", caption, payload.getOrdinal() + 1);
        }

        public List<PayloadField> getTrack() {
            LinkedList<PayloadField> track = new LinkedList<>();

            PayloadField field = this;
            while (field != null) {
                field = field.getEditor().field;
                track.addFirst(field);
            }


            return track;
        }

        public String getFoobar() {
            String foobar = getTrack().stream()
                    .filter(Objects::nonNull)
                    .map(PayloadField::getCaption)
                    .collect(Collectors.joining(" / "));

//            if (StringUtils.hasText(foobar)) {
//                foobar = String.format("%s /", foobar);
//            }

            return foobar;
        }

        void refresh() {
            Attribute attribute = group.getAttribute();
            Set<PayloadField> fields = group.fields;

            Actions actions = getActions();
            actions.clear();

            if (attribute instanceof ItemAttribute) {
                Action compressAction = new Action();
                compressAction.setText(new StaticText("<i class=\"fa fa-compress\"></i>"));
                compressAction.setUrl("@{compress-payload(payload=${field.payload.uuid})}");
                compressAction.setDisabled(isCompressed());
                actions.add(compressAction);

                Action expandAction = new Action();
                expandAction.setText(new StaticText("<i class=\"fa fa-expand\"></i>"));
                expandAction.setUrl("@{expand-payload(payload=${field.payload.uuid})}");
                expandAction.setDisabled(!isCompressed());
                actions.add(expandAction);
            }


            Action moveUpAction = new Action();
            moveUpAction.setText(new StaticText("<i class=\"fa fa-arrow-up\"></i>"));
            moveUpAction.setUrl("@{move-payload-up(payload=${field.payload.uuid})}");
            moveUpAction.setDisabled(payload.getOrdinal() <= 0);
            actions.add(moveUpAction);

            Action moveDownAction = new Action();
            moveDownAction.setText(new StaticText("<i class=\"fa fa-arrow-down\"></i>"));
            moveDownAction.setUrl("@{move-payload-down(payload=${field.payload.uuid})}");
            moveDownAction.setDisabled(payload.getOrdinal() >= fields.size() - 1);
            actions.add(moveDownAction);

            Action removeAction = new Action();
            removeAction.setText(new StaticText("<i class=\"fa fa-remove\"></i>"));
            removeAction.setUrl("@{remove-payload(payload=${field.payload.uuid})}");
            actions.add(removeAction);

            if (nestedEditor != null) {
                nestedEditor.refresh();
            }
        }

        public PayloadField(PayloadFieldGroup group, Payload payload) {
            this.group = group;
            this.payload = payload;
        }
    }

    public static class PayloadFieldGroup {

        public static class PayloadGroupAction extends Action {

            private Text description;

            public Text getDescription() {
                return description;
            }

            public void setDescription(Text description) {
                this.description = description;
            }
        }

        private final NestedItemEditor editor;
        private final String attributeName;
        private Actions actions;
        private String payloadTemplate;
        private String template = "admin/item/editor/field-group";

        private final Set<PayloadField> fields = new HashSet<>();

        public NestedItemEditor getEditor() {
            return editor;
        }

        private final ItemService getItemService() {
            return editor.getItemService();
        }

        private final Item getItem() {
            return editor.getItem();
        }

        public String getAttributeName() {
            return attributeName;
        }

        public Attribute getAttribute() {
            Item item = getItem();
            return getItemService().getAttribute(item, attributeName);
        }

        public Actions getActions() {
            if (actions == null) {
                actions = new Actions();
            }

            return actions;
        }

        public void setActions(Actions actions) {
            this.actions = actions;
        }

        public String getPayloadTemplate() {
            return payloadTemplate;
        }

        public void setPayloadTemplate(String payloadTemplate) {
            this.payloadTemplate = payloadTemplate;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public Set<PayloadField> getFields() {
            return fields.stream()
                    .sorted(Comparator.comparingInt(it -> it.getPayload().getOrdinal()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        public boolean addField(PayloadField field) {
            if (fields.add(field)) {
                editor.getRootEditor().attachField(field);
                return true;
            }

            return false;
        }

        public boolean removeField(PayloadField field) {
            if (fields.remove(field)) {
                editor.getRootEditor().detachField(field);
                return true;
            }

            return false;
        }

        @UsedInTemplates
        public PayloadField getField(Payload payload) {
            Assert.notNull(payload, "[payload] must not be null");
            return fields.stream()
                    .filter(it -> it.payload.getUuid().equals(payload.getUuid()))
                    .findFirst().orElse(null);
        }

        void refresh() {
            Actions actions = getActions();
            actions.clear();

            Attribute attribute = getAttribute();
            List<Payload> payloads = getItemService().getPayloads(getItem(), getAttributeName());
            if (payloads != null && payloads.size() < attribute.getCapacity()) {
                if (attribute instanceof ItemAttribute) {
                    actions.setText(new StaticText("Append"));
                    for (String structureName : ((ItemAttribute) attribute).getAcceptedStructureNames()) {
                        PayloadGroupAction action = new PayloadGroupAction();
                        ResolvableText actionText = new ResolvableText(getItemService().getMessageSource());
                        actionText.setDefaultMessage(structureName);
                        actionText.setMessageCode(String.format("seed.structure.%s", structureName));
                        ResolvableText actionDescription = new ResolvableText(getItemService().getMessageSource());
                        actionDescription.setMessageCode(String.format("seed.structure.%s.description", structureName));
                        action.setDescription(actionDescription);
                        action.setText(actionText);
                        action.setUrl("/admin/item/append" +
                                "?item=" + getItem().getUuid() +
                                "&attribute=" + attributeName +
                                "&structure=" + structureName);
                        actions.add(action);
                    }
                } else {
                    Action action1 = new Action();
                    action1.setText(new StaticText(attributeName));
                    action1.setUrl("/admin/item/append" +
                            "?item=" + getItem().getUuid() +
                            "&attribute=" + attributeName);
                    actions.add(action1);
                }
            }

            for (PayloadField field : fields) {
                field.refresh();
            }
        }

        public PayloadFieldGroup(NestedItemEditor editor, String attributeName) {
            this.editor = editor;
            this.attributeName = attributeName;
        }
    }

    private final PayloadField field;
    private Item item;

    private final Set<PayloadFieldGroup> fieldGroups = new LinkedHashSet<>();

    protected NestedItemEditor getParent() {
        if (field != null) {
            return field.getEditor();
        }

        return null;
    }

    protected ItemEditor getRootEditor() {
        NestedItemEditor parent = getParent();
        if (parent == null) {
            return (ItemEditor) this;
        }

        return parent.getRootEditor();
    }

    protected ItemService getItemService() {
        return getRootEditor().getItemService();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        Item oldItem = this.item;
        if (oldItem != null) {
            oldItem.removeChangeListener(this);
        }

        this.item = item;

        if (item == null) {
            return;
        }

        removeAllFieldGroups();

        item.addChangeListener(this);

        Structure structure = getItemService().getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            ItemEditor.PayloadFieldGroup fieldGroup = new ItemEditor.PayloadFieldGroup(this, attributeName);
            addFieldGroup(fieldGroup);

            Attribute attribute = structure.getAttribute(attributeName);
            String payloadTemplate;

            if (attribute instanceof TextAttribute) {
                boolean multiline = ((TextAttribute) attribute).isMultiline();
                if (multiline) {
                    payloadTemplate = "admin/item/editor/field::multiline-text";
                } else {
                    payloadTemplate = "admin/item/editor/field::text";
                }
            } else if (attribute instanceof ItemAttribute) {
                payloadTemplate = "admin/item/editor/field::item";
            } else if (attribute instanceof LinkAttribute) {
                payloadTemplate = "admin/item/editor/field::link";
            } else if (attribute instanceof IconAttribute) {
                payloadTemplate = "admin/item/editor/field::icon";
            } else if (attribute instanceof ImageAttribute) {
                payloadTemplate = "admin/item/editor/field::image";
            } else if (attribute instanceof YesNoAttribute) {
                payloadTemplate = "admin/item/editor/field::yesno";
            } else if (attribute instanceof SelectionAttribute) {
                payloadTemplate = "admin/item/editor/field::selection";
            } else if (attribute instanceof DateAttribute) {
                payloadTemplate = "admin/item/editor/field::date";
            } else {
                throw new RuntimeException("Unexpected attribute type: " + attribute.getClass());
            }

            fieldGroup.setPayloadTemplate(payloadTemplate);

            fieldGroup.refresh();
        }

        // Initial
        for (String attributeName : item.getAttributeNames()) {
            PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
            for (Payload payload : payloadGroup.getPayloads()) {
                payloadAdded(item, payload);
            }
        }
    }

    @Override
    public void payloadAdded(Item item, Payload payload) {
        if (this.item != item)
            return;

        PayloadGroup payloadGroup = payload.getGroup();
        PayloadFieldGroup fieldGroup = findFieldGroup(payloadGroup.getAttributeName());
        if (fieldGroup != null) {
            PayloadField field = new PayloadField(fieldGroup, payload);
            field.setCompressed(false);

            Attribute attribute = getItemService().getAttribute(payload);
            if (attribute instanceof ItemAttribute) {
                Item nestedItem = ((ItemPayload) payload).getItem();
                NestedItemEditor nestedEditor = new NestedItemEditor(field);
                nestedEditor.setItem(nestedItem);
                field.setNestedEditor(nestedEditor);

                if (nestedItem.getId() != null) {
                    field.setCompressed(true);
                }
            }

            fieldGroup.addField(field);
            fieldGroup.refresh();
        }
    }

    @Override
    public void payloadRemoved(Item item, Payload payload) {
        PayloadGroup payloadGroup = payload.getGroup();
        String attributeName = payloadGroup.getAttributeName();
        PayloadFieldGroup fieldGroup = findFieldGroup(attributeName);
        PayloadField field = fieldGroup.getField(payload);
        fieldGroup.removeField(field);
    }

    public Set<PayloadFieldGroup> getFieldGroups() {
        return Collections.unmodifiableSet(fieldGroups);
    }

    private void addFieldGroup(PayloadFieldGroup fieldGroup) {
        fieldGroups.add(fieldGroup);
    }

    private PayloadFieldGroup findFieldGroup(String attributeName) {
        for (PayloadFieldGroup fieldGroup : fieldGroups) {
            if (attributeName.equals(fieldGroup.attributeName)) {
                return fieldGroup;
            }

            for (PayloadField field : fieldGroup.getFields()) {
                NestedItemEditor nestedEditor = field.nestedEditor;
                if (nestedEditor != null) {
                    PayloadFieldGroup found = nestedEditor.findFieldGroup(attributeName);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }

        return null;
    }

    public void removeAllFieldGroups() {
        fieldGroups.clear();
    }

    public PayloadField findField(Payload payload) {
        for (PayloadFieldGroup fieldGroup : fieldGroups) {
            for (PayloadField field : fieldGroup.getFields()) {
                if (field.payload == payload) {
                    return field;
                }

                NestedItemEditor nestedEditor = field.nestedEditor;
                if (nestedEditor != null) {
                    PayloadField found = nestedEditor.findField(payload);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }

        return null;
    }

    public void refresh() {
        for (PayloadFieldGroup fieldGroup : fieldGroups) {
            fieldGroup.refresh();
        }
    }

    public NestedItemEditor(PayloadField field) {
        this.field = field;
    }
}
