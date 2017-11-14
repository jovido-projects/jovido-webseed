package biz.jovido.seed.content.admin;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.Action;
import biz.jovido.seed.ui.ActionGroup;
import biz.jovido.seed.ui.Actions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class ItemEditor2 implements HasTemplate {

    @Override
    public String getTemplate() {
        return "admin/item/editor2";
    }

    public static abstract class PayloadField implements HasTemplate {

        private final PayloadFieldGroup fieldGroup;
        private final Payload payload;
        private String id;
        private ActionGroup actions = new ActionGroup();

        public PayloadFieldGroup getFieldGroup() {
            return fieldGroup;
        }

        public Payload getPayload() {
            return payload;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBindingPath() {
            return String.format("fieldGroups[%s].fields[%d]",
                    getFieldGroup().getAttributeName(),
                    getPayload().getOrdinal());
        }

        public void remove() {
            payload.remove();
            fieldGroup.removeField(this);
        }

        public void moveUp() {
            PayloadGroup payloadGroup = payload.getGroup();
            int index = payload.getOrdinal();
            payloadGroup.movePayload(index, index-1);
        }

        public PayloadField(PayloadFieldGroup fieldGroup, Payload payload) {
            this.fieldGroup = fieldGroup;
            this.payload = payload;
        }
    }

    public static class ItemEditorField extends PayloadField {

        private ItemEditor2 editor;

        @Override
        public ItemRelation getPayload() {
            return (ItemRelation) super.getPayload();
        }

        @Override
        public String getTemplate() {
            return "admin/item/editor2/item-editor-field";
        }

        public ItemEditorField(PayloadFieldGroup fieldGroup, ItemRelation payload) {
            super(fieldGroup, payload);
        }
    }

    public static class TextField extends PayloadField {

        public Text getPayload() {
            return (Text) super.getPayload();
        }

        @Override
        public String getTemplate() {
            return "admin/item/editor2/text-field";
        }

        public TextField(PayloadFieldGroup fieldGroup, Payload payload) {
            super(fieldGroup, payload);
        }
    }

    public static class ImageField extends PayloadField {

        public ImageRelation getPayload() {
            return (ImageRelation) super.getPayload();
        }

        public Image getImage() {
            return getPayload().getTarget();
        }

        @Override
        public String getTemplate() {
            return "admin/item/editor2/image-field";
        }

        public ImageField(PayloadFieldGroup fieldGroup, Payload payload) {
            super(fieldGroup, payload);
        }
    }

    public static class YesNoField extends PayloadField {

        public YesNo getPayload() {
            return (YesNo) super.getPayload();
        }

        @Override
        public String getTemplate() {
            return "admin/item/editor2/yesno-field";
        }

        public YesNoField(PayloadFieldGroup fieldGroup, Payload payload) {
            super(fieldGroup, payload);
        }
    }

    public static class LinkField extends PayloadField {

        public Link getPayload() {
            return (Link) super.getPayload();
        }

        @Override
        public String getTemplate() {
            return "admin/item/editor2/link-field";
        }

        public LinkField(PayloadFieldGroup fieldGroup, Payload payload) {
            super(fieldGroup, payload);
        }
    }

    public static class PayloadFieldGroup implements HasTemplate {

        private final ItemEditor2 editor;
        private final String attributeName;
        private final Actions actions = new Actions();

        private final Set<PayloadField> fields = new HashSet<>();

        public ItemEditor2 getEditor() {
            return editor;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public Actions getActions() {
            return actions;
        }

        @Deprecated
        public PayloadGroup getPayloadGroup() {
            return editor.item.getPayloadGroup(attributeName);
        }

        public Attribute getAttribute() {
            return editor.itemService.getAttribute(getPayloadGroup());
        }

        public List<PayloadField> getFields() {
            List<PayloadField> list = fields.stream()
                    .sorted(Comparator.comparingInt(it -> it.getPayload().getOrdinal()))
                    .collect(Collectors.toList());

            return Collections.unmodifiableList(list);
        }

        public PayloadField addField(Payload payload) {
            PayloadField field;// = new PayloadField(this, payload);

            if (payload instanceof YesNo) {
                field = new YesNoField(this, payload);
            } else if (payload instanceof Text) {
                field = new TextField(this, payload);
            } else if (payload instanceof Link) {
                field = new LinkField(this, payload);
            } else if (payload instanceof ImageRelation) {
                field = new ImageField(this, payload);
            } else if (payload instanceof ItemRelation) {
                field = new ItemEditorField(this, (ItemRelation) payload);
            } else {
                throw new RuntimeException("Unexpected payload type: " + payload.getClass());
            }

            field.setId(UUID.randomUUID().toString());
            fields.add(field);

//            editor.fields.put(field.getId(), field);

            return field;
        }

        public boolean removeField(PayloadField field) {
            return fields.remove(field);
        }

        public PayloadFieldGroup(ItemEditor2 editor, String attributeName) {
            this.editor = editor;
            this.attributeName = attributeName;
        }

        @Override
        public String getTemplate() {
            return "admin/item/editor2/field-group";
        }
    }

    private final ItemService itemService;
    private Item item;

    private final Set<PayloadFieldGroup> fieldGroups = new LinkedHashSet<>();

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;

        fieldGroups.clear();

        Structure structure = itemService.getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
            PayloadFieldGroup fieldGroup = addFieldGroup(attributeName);
            Actions actions = fieldGroup.getActions();
            Action appendAction = new Action();
            appendAction.setDefaultMessage("Append " + attributeName);
            appendAction.setUrl("/admin/item2/add-field?field-group=" + attributeName);
            actions.add(appendAction);
            for (Payload payload : payloadGroup.getPayloads()) {
                fieldGroup.addField(payload);
            }
        }

        item.addChangeListener(new ItemChangeListener() {
            @Override
            public void payloadAdded(Item item, Payload payload) {
                String attributeName = payload.getGroup().getAttributeName();
                PayloadFieldGroup fieldGroup = getFieldGroups().get(attributeName);
                fieldGroup.addField(payload);
            }

            @Override
            public void payloadRemoved(Item item, Payload payload) {

            }
        });
    }

    public Map<String, PayloadFieldGroup> getFieldGroups() {
        Map<String, PayloadFieldGroup> map = fieldGroups.stream()
                .collect(Collectors.toMap(PayloadFieldGroup::getAttributeName, k -> k));

        return Collections.unmodifiableMap(map);
    }

    public PayloadFieldGroup getFieldGroup(String attributeName) {
        return fieldGroups.stream()
                .filter(it -> attributeName.equals(it.attributeName))
                .findFirst().orElse(null);
    }

    public PayloadFieldGroup addFieldGroup(String attributeName) {
        PayloadFieldGroup fieldGroup = new PayloadFieldGroup(this, attributeName);
        fieldGroups.add(fieldGroup);
        return fieldGroup;
    }

    public boolean removeFieldGroup(String attributeName) {
        return fieldGroups.removeIf(it -> it.attributeName.equals(attributeName));
    }

    public PayloadField findField(String id) {
        for (PayloadFieldGroup fieldGroup : fieldGroups) {
            for (PayloadField field : fieldGroup.fields) {
                if (field.getId().equals(id)) {
                    return field;
                }
            }
        }

        return null;
    }

    public PayloadField findField(Payload payload) {
        for (PayloadFieldGroup fieldGroup : fieldGroups) {
            for (PayloadField field : fieldGroup.fields) {
                if (field.payload == payload) {
                    return field;
                }
            }
        }

        return null;
    }

    public ItemEditor2(ItemService itemService) {
        this.itemService = itemService;
    }
}
