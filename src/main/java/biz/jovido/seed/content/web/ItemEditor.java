package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.Item;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    private final ItemService itemService;

    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemEditor(ItemService itemService) {
        this.itemService = itemService;
    }
}
