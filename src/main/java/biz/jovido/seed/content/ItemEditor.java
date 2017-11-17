package biz.jovido.seed.content;

import biz.jovido.seed.uimodel.Action;
import biz.jovido.seed.uimodel.Actions;
import biz.jovido.seed.uimodel.StaticText;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class ItemEditor implements ItemChangeListener {

    public static class PayloadField {

        private final PayloadFieldGroup group;
        private final Payload payload;
        private ItemEditor nestedEditor;

        private Actions actions;
        private boolean compressed;

        public ItemEditor getNestedEditor() {
            return nestedEditor;
        }

        public void setNestedEditor(ItemEditor nestedEditor) {
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

        void refresh() {
            Attribute attribute = group.getAttribute();

            Actions actions = getActions();
            actions.clear();

            if (payload instanceof ItemRelation) {
                Action compressAction = new Action();
                compressAction.setText(new StaticText("<i class=\"fa fa-compress\"></i>"));
                compressAction.setUrl("@{compress-payload(payload=${payload.uuid})}");
                compressAction.setDisabled(isCompressed());
                actions.add(compressAction);

                Action expandAction = new Action();
                expandAction.setText(new StaticText("<i class=\"fa fa-expand\"></i>"));
                expandAction.setUrl("@{expand-payload(payload=${payload.uuid})}");
                expandAction.setDisabled(!isCompressed());
                actions.add(expandAction);
            }


            Action moveUpAction = new Action();
            moveUpAction.setText(new StaticText("<i class=\"fa fa-arrow-up\"></i>"));
            moveUpAction.setUrl("@{move-payload-up(payload=${payload.uuid})}");
            moveUpAction.setDisabled(payload.getOrdinal() <= 0);
            actions.add(moveUpAction);

            Action moveDownAction = new Action();
            moveDownAction.setText(new StaticText("<i class=\"fa fa-arrow-down\"></i>"));
            moveDownAction.setUrl("@{move-payload-down(payload=${payload.uuid})}");
            moveDownAction.setDisabled(payload.getOrdinal() >= attribute.getCapacity() - 1);
            actions.add(moveDownAction);

            Action removeAction = new Action();
            removeAction.setText(new StaticText("<i class=\"fa fa-remove\"></i>"));
            removeAction.setUrl("@{remove-payload(payload=${payload.uuid})}");
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

        private final ItemEditor editor;
        private final String attributeName;
        private Actions actions;
        private String payloadTemplate;

        private final Set<PayloadField> fields = new HashSet<>();

        private Item getItem() {
            return editor.getItem();
        }

        public PayloadGroup getPayloadGroup() {
            return getItem().getPayloadGroup(attributeName);
        }

        public Attribute getAttribute() {
            return editor.itemService.getAttribute(getPayloadGroup());
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

        public Set<PayloadField> getFields() {
            return Collections.unmodifiableSet(fields);
        }

        public boolean addField(PayloadField field) {
            return fields.add(field);
        }

        public PayloadField getField(Payload payload) {
            return fields.stream()
                    .filter(it -> it.payload.getUuid().equals(payload.getUuid()))
                    .findFirst().orElse(null);
        }

        void refresh() {
            PayloadGroup payloadGroup = getPayloadGroup();
            Actions actions = getActions();
            actions.clear();

            Attribute attribute = editor.itemService.getAttribute(payloadGroup);
            if (attribute.getCapacity() > 1) {
                if (attribute instanceof ItemAttribute) {
                    actions.setText(new StaticText("Append"));
                    for (String structureName : ((ItemAttribute) attribute).getAcceptedStructureNames()) {
                        Action action = new Action();
                        action.setText(new StaticText(structureName));
                        action.setUrl("/admin/item/append" +
                                "?payload-group=" + payloadGroup.getUuid() +
                                "&structure=" + structureName);
                        actions.add(action);
                    }
                } else {
                    Action action1 = new Action();
                    action1.setText(new StaticText("Simple value"));
                    action1.setUrl("/admin/item/append" +
                            "?payload-group=" + payloadGroup.getUuid());
                    actions.add(action1);
                }
            }

            for (PayloadField field : fields) {
                field.refresh();
            }
        }

        public PayloadFieldGroup(ItemEditor editor, String attributeName) {
            this.editor = editor;
            this.attributeName = attributeName;
        }
    }

    private final ItemService itemService;
    private Item item;

    private final Set<PayloadFieldGroup> fieldGroups = new LinkedHashSet<>();

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

        for (String attributeName : item.getAttributeNames()) {
            ItemEditor.PayloadFieldGroup fieldGroup = new ItemEditor.PayloadFieldGroup(this, attributeName);
            addFieldGroup(fieldGroup);

            Structure structure = itemService.getStructure(item);
            Attribute attribute = structure.getAttribute(attributeName);
            String payloadTemplate;

            if (attribute instanceof TextAttribute) {
                boolean multiline = ((TextAttribute) attribute).isMultiline();
                if (multiline) {
                    payloadTemplate = "admin/item/editor/payload::multiline-text";
                } else {
                    payloadTemplate = "admin/item/editor/payload::text";
                }
            } else if (attribute instanceof ItemAttribute) {
                payloadTemplate = "admin/item/editor/payload::item";
            } else if (attribute instanceof LinkAttribute) {
                payloadTemplate = "admin/item/editor/payload::link";
            } else if (attribute instanceof IconAttribute) {
                payloadTemplate = "admin/item/editor/payload::icon";
            } else if (attribute instanceof ImageAttribute) {
                payloadTemplate = "admin/item/editor/payload::image";
            } else if (attribute instanceof YesNoAttribute) {
                payloadTemplate = "admin/item/editor/payload::yesno";
            } else {
                throw new RuntimeException("Unexpected attribute type: " + attribute.getClass());
            }

            fieldGroup.setPayloadTemplate(payloadTemplate);

            fieldGroup.refresh();

            PayloadGroup payloadGroup = fieldGroup.getPayloadGroup();

            // Initial
            for (Payload payload : payloadGroup.getPayloads()) {
                payloadAdded(item, payload);
            }
        }
    }

    @Override
    public void payloadAdded(Item item, Payload payload) {
        if (this.item != item)
            return;

        PayloadFieldGroup fieldGroup = getFieldGroup(payload.getGroup());
        PayloadField field = new PayloadField(fieldGroup, payload);
        field.setCompressed(false);

        if (payload instanceof ItemRelation) {
            Item nestedItem = ((ItemRelation) payload).getTarget();
            ItemEditor nestedEditor = new ItemEditor(itemService);
            nestedEditor.setItem(nestedItem);
            field.setNestedEditor(nestedEditor);

            if (nestedItem.getId() != null) {
                field.setCompressed(true);
            }
        }

        fieldGroup.addField(field);
        fieldGroup.refresh();
    }

    @Override
    public void payloadRemoved(Item item, Payload payload) {
        throw new UnsupportedOperationException();
    }

    public Set<PayloadFieldGroup> getFieldGroups() {
        return Collections.unmodifiableSet(fieldGroups);
    }

    public void addFieldGroup(PayloadFieldGroup fieldGroup) {
        fieldGroups.add(fieldGroup);
    }

    public PayloadFieldGroup getFieldGroup(PayloadGroup payloadGroup) {
        for (PayloadFieldGroup fieldGroup : fieldGroups) {
            if (payloadGroup == fieldGroup.getPayloadGroup()) {
                return fieldGroup;
            }

            for (PayloadField field : fieldGroup.getFields()) {
                ItemEditor nestedEditor = field.nestedEditor;
                if (nestedEditor != null) {
                    PayloadFieldGroup found = nestedEditor.getFieldGroup(payloadGroup);
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

    public PayloadField getField(Payload payload) {
        for (PayloadFieldGroup fieldGroup : fieldGroups) {
            for (PayloadField field : fieldGroup.getFields()) {
                if (field.payload == payload) {
                    return field;
                }

                ItemEditor nestedEditor = field.nestedEditor;
                if (nestedEditor != null) {
                    PayloadField found = nestedEditor.getField(payload);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }

        return null;
    }

    public void refresh() {
        for (PayloadFieldGroup fieldGroup : getFieldGroups()) {
            fieldGroup.refresh();
        }
    }

    public ItemEditor(ItemService itemService) {
        this.itemService = itemService;
    }
}
