package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Item;
import biz.jovido.seed.content.model.Property;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class ItemForm {

    private final ItemMaintenance maintenance;

    private Item item;

    private final Map<String, PropertyEditor> editors = new HashMap<>();

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;

        init();
    }

    public Map<String, PropertyEditor> getEditors() {
        return Collections.unmodifiableMap(editors);
    }

    public void init() {
        editors.clear();

        for (Property property : item.getProperties().values()) {
            PropertyEditor editor = editors.get(property.getName());
            if (editor == null) {
                editor = new PropertyEditor();
                editor.form = this;
                editor.propertyName = property.getName();
                editors.put(property.getName(), editor);

                editor.refresh();
            }
        }
    }

    public ItemForm(ItemMaintenance maintenance) {
        this.maintenance = maintenance;
    }
}
