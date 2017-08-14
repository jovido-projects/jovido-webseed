package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class ItemForm {

    final ItemEditor editor;

    private final Map<String, PropertyField> fields = new HashMap<>();

    public Map<String, PropertyField> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public PropertyField getField(String name) {
        return fields.get(name);
    }

    public void setField(String name, PropertyField field) {
        PropertyField replaced = fields.put(name, field);

        if (replaced != null) {
            replaced.form = null;
            replaced.name = null;
        }

        if (field != null) {
            field.form = this;
            field.name = name;
        }
    }

    public Item getItem() {
        return editor.item;
    }

    public ItemForm(ItemEditor editor) {
        this.editor = editor;
    }
}
