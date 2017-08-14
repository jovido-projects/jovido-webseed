package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.*;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    private final ItemService itemService;

    Item item;

    private ItemForm form;

    public Item getItem() {
        return item;
    }

    public ItemForm getForm() {
        return form;
    }

    public Structure getStructure() {
        return itemService.getStructure(item);
    }


    public void create(String structureName, Locale locale) {
        item = itemService.createItem(structureName, locale);
        form = new ItemForm(this);

        Structure structure = getStructure();
        for (Attribute attribute : structure.getAttributes()) {
            String name = attribute.getName();
            Property property = item.getProperty(name);
            PropertyField field = new PropertyField();
            for (Payload value : property.getPayloads()) {
                if (value instanceof RelationPayload) {

                } else {
                    PayloadValue payload = new PayloadValue();
                    payload.setPayload(value);
                    field.addPayload(payload);
                }
            }
            field.setProperty(property);
            form.setField(name, field);
        }
    }

    public ItemEditor(ItemService itemService) {
        this.itemService = itemService;
    }
}
