package biz.jovido.seed.content.frontend;

import biz.jovido.seed.content.Item;

/**
 * @author Stephan Grundner
 */
public final class ItemValues extends AbstractValuesMap<ValuesList> {

    private final Item item;
    private final ItemValues parent;

    public Item getItem() {
        return item;
    }

    public ItemValues getParent() {
        return parent;
    }

    public ItemValues(Item item, ItemValues parent) {
        this.item = item;
        this.parent = parent;
    }

    public ItemValues(Item item) {
        this(item, null);
    }
}
