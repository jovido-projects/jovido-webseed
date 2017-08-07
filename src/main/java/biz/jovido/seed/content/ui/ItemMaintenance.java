package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class ItemMaintenance {

    private final ItemService itemService;

    private Item item;

    private Structure structure;
    private Map<String, Field> fieldByAttributeName = new HashMap<>();


    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<Field> getFields() {
        return fieldByAttributeName.values().stream()
                .collect(Collectors.toList());
    }

    public void create(String structureName, Locale locale) {
        item = itemService.createItem(structureName, locale);
        structure = itemService.getStructure(item);

        for (Attribute attribute : structure.getAttributes()) {
            Field field = null;
            if (attribute instanceof TextAttribute) {
                field = new TextField();
                ((TextField) field).setAttributeName(attribute.getName());
            }

            fieldByAttributeName.put(attribute.getName(), field);
        }
    }

    public ItemMaintenance(ItemService itemService) {
        this.itemService = itemService;
    }
}
