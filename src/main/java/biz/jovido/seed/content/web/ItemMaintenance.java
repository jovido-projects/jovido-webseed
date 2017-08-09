package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.Item;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public class ItemMaintenance {

    protected final ItemService itemService;

    private final ItemForm form = new ItemForm(this);

    public ItemForm getForm() {
        return form;
    }

    public void create(String structureName, Locale locale) {
        Item item = itemService.createItem(structureName, locale);
        form.setItem(item);
    }

    public void append(String attributeName, )

    public ItemMaintenance(ItemService itemService) {
        this.itemService = itemService;
    }
}
