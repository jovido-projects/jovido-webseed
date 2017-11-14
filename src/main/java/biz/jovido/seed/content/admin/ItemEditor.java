package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.Item;
import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.Structure;
import org.apache.commons.collections4.map.LazyMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    protected final ItemService itemService;

    private Item item;
    private final Map<String, PayloadFieldGroup> fieldGroups = LazyMap.lazyMap(new HashMap<String, PayloadFieldGroup>(),
            attributeName -> new PayloadFieldGroup(ItemEditor.this, attributeName));

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;

        fieldGroups.clear();
    }

    public Map<String, PayloadFieldGroup> getFieldGroups() {
        return Collections.unmodifiableMap(fieldGroups);
    }

    public Structure getStructure() {
        return itemService.getStructure(item);
    }

    public ItemEditor(Item item, ItemService itemService) {
        this.item = item;
        this.itemService = itemService;
    }
}
