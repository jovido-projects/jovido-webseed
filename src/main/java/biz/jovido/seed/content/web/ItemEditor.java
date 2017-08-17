package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    private final ItemService itemService;

    private Item item;

    private final Map<String, Field> fields = new HashMap<>();

    public ItemService getItemService() {
        return itemService;
    }

    public Item getItem() {
        return item;
    }

    public Structure getStructure() {
        Item item = getItem();
        return itemService.getStructure(item);
    }

    public void setItem(Item item) {
        this.item = item;

        Structure structure = getStructure();
        for (Attribute attribute : structure.getAttributes()) {
            Field field;
            if (attribute instanceof RelationAttribute) {
                RelationsField relationField = new RelationsField();
                for (String structureName : ((RelationAttribute) attribute).getAcceptedStructureNames()) {
                    Structure allowedStructure = itemService.getStructure(structureName);
                    relationField.getAllowedStructures().add(allowedStructure);
                }

                field = relationField;
            } else {
                field = new ValueField();
            }
            String attributeName = attribute.getName();
            Payload payload = item.getPayload(attributeName);
            field.setPayload(payload);
            putField(field);
        }
    }

    public Map<String, Field> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public Field getField(String name) {
        return fields.get(name);
    }

    public Field putField(Field field) {
        field.setEditor(this);
        String attributeName = field.getAttribute().getName();
        Field replaced = fields.put(attributeName, field);

        if (replaced != null) {
            replaced.setEditor(null);
        }



        return replaced;
    }

    public ItemEditor(ItemService itemService) {
        this.itemService = itemService;
    }
}
