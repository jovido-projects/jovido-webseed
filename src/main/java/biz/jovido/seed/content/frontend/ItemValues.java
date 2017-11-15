package biz.jovido.seed.content.frontend;

import biz.jovido.seed.content.Item;

/**
 * @author Stephan Grundner
 */
public final class ItemValues extends AbstractValuesMap<ValuesList> {

    private final Item item;

    public Item getItem() {
        return item;
    }

    public ItemValues(Item item) {
        this.item = item;
    }
}
