package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.Action;
import biz.jovido.seed.ui.Actions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class NestedItemEditor extends PayloadField {

    protected final ItemService itemService;
    private Item item;

    private final Map<String, PayloadFieldGroup> fieldGroups = new HashMap<>();

    private PayloadField addFieldFor(Payload payload) {
        String attributeName = payload.getGroup().getAttributeName();
        PayloadFieldGroup fieldGroup = fieldGroups.get(attributeName);
        return fieldGroup.addFieldFor(payload);
    }

    private boolean removeFieldFor(Payload payload) {
        String attributeName = payload.getGroup().getAttributeName();
        PayloadFieldGroup fieldGroup = fieldGroups.get(attributeName);
        return fieldGroup.removeFieldFor(payload);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;

        fieldGroups.clear();

        if (item != null) {
            Structure structure = itemService.getStructure(item);
            for (String attributeName : structure.getAttributeNames()) {
                Attribute attribute = structure.getAttribute(attributeName);
                PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
                PayloadFieldGroup fieldGroup = new PayloadFieldGroup(this, attributeName);
                fieldGroup.setTemplate("admin/item/editor/field-group");
                Actions actions = fieldGroup.getActions();

                if (attribute instanceof ItemAttribute) {
                    actions.getText().setDefaultMessage("Append ...");
                    for (String structureName : ((ItemAttribute) attribute).getAcceptedStructureNames()) {
                        Action action = new Action();
                        action.setDefaultMessage(structureName);
                        action.setUrl("/admin/item/create-and-append-item" +
                                "?field-group=" + fieldGroup.getId() +
                                "&structure=" + structureName);
                        actions.add(action);
                    }
                } else {
                    Action action = new Action();
                    action.setDefaultMessage("Append " + attributeName);
                    action.setUrl("/admin/item/append-field?field-group=" + fieldGroup.getId());
                    actions.add(action);
                }

                if (attribute instanceof YesNoAttribute) {
                    fieldGroup.setLabelVisible(false);
                }

                fieldGroups.put(attributeName, fieldGroup);
                for (Payload payload : payloadGroup.getPayloads()) {
                    addFieldFor(payload);
                }
            }

            item.addChangeListener(new ItemChangeListener() {
                @Override
                public void payloadAdded(Item item, Payload payload) {
                    if (item == NestedItemEditor.this.item) {
                        addFieldFor(payload);
                    }
                }

                @Override
                public void payloadRemoved(Item item, Payload payload) {
                    if (item == NestedItemEditor.this.item) {
                        removeFieldFor(payload);
                    }
                }
            });
        }
    }

    public Map<String, PayloadFieldGroup> getFieldGroups() {
        return Collections.unmodifiableMap(fieldGroups);
    }

    @Override
    public void setPayload(Payload payload) {
//        if (!(payload instanceof ItemRelation)) {
//            throw new IllegalArgumentException();
//        }
        if (payload != null)
        setItem(((ItemRelation) payload).getTarget());

        super.setPayload(payload);
    }

    public Structure getStructure() {
        return itemService.getStructure(getItem());
    }

    public PayloadFieldGroup findFieldGroup(String id) {
        for (PayloadFieldGroup fieldGroup : getFieldGroups().values()) {
            if (fieldGroup.getId().equals(id)) {
                return fieldGroup;
            }

            for (PayloadField field : fieldGroup.getFields()) {
                if (field instanceof NestedItemEditor) {
                    fieldGroup = ((NestedItemEditor) field).findFieldGroup(id);
                    if (fieldGroup != null) {
                        return fieldGroup;
                    }
                }
            }
        }

        return null;
    }

    public PayloadField findField(String id) {
        for (PayloadFieldGroup fieldGroup : getFieldGroups().values()) {
            PayloadField field = fieldGroup.findField(id);
            if (field != null) {
                return field;
            }
        }

        return null;
    }

    @Override
    public String getCaption() {
        Payload payload = getPayload();
        if (payload != null) {
            return getStructure().getName() + " " + (payload.getOrdinal() + 1);
        } else {
            return getStructure().getName();
        }
    }

    public NestedItemEditor(PayloadFieldGroup group, ItemService itemService) {
        super(group);
        this.itemService = itemService;
    }
}
