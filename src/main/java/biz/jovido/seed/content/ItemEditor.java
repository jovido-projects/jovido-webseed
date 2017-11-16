package biz.jovido.seed.content;

import biz.jovido.seed.uimodel.Action;
import biz.jovido.seed.uimodel.Actions;
import biz.jovido.seed.uimodel.StaticText;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class ItemEditor implements ItemChangeListener {

    public static class PayloadFieldGroup {

        private final ItemEditor editor;
        private final String attributeName;
        private Actions actions;
        private String payloadTemplate;

        private final Map<UUID, ItemEditor> nestedEditors = new HashMap<>();

        private Item getItem() {
            return editor.getItem();
        }

        public PayloadGroup getPayloadGroup() {
            return getItem().getPayloadGroup(attributeName);
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

        public Map<UUID, ItemEditor> getNestedEditors() {
            return Collections.unmodifiableMap(nestedEditors);
        }

        public void addNestedEditor(UUID uuid, ItemEditor nestedEditor) {
            nestedEditors.put(uuid, nestedEditor);
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

        if (payload instanceof ItemRelation) {
            Item nestedItem = ((ItemRelation) payload).getTarget();
            ItemEditor nestedEditor = new ItemEditor(itemService);
            nestedEditor.setItem(nestedItem);
            fieldGroup.addNestedEditor(payload.getUuid(), nestedEditor);
        }

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

            for (ItemEditor nestedEditor : fieldGroup.nestedEditors.values()) {
                PayloadFieldGroup found = nestedEditor.getFieldGroup(payloadGroup);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    public void removeAllFieldGroups() {
        fieldGroups.clear();
    }

    public ItemEditor(ItemService itemService) {
        this.itemService = itemService;
    }
}
