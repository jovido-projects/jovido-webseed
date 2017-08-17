package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Attribute;
import biz.jovido.seed.content.model.Property;
import biz.jovido.seed.content.model.Structure;
import biz.jovido.seed.web.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class PropertyField implements Field<ItemEditor, PayloadValue> {

    String name;
    ItemEditor editor;

    private Property property;

    private final List<PayloadValue> values = new ArrayList<>();

    public String getName() {
        return name;
    }

    public ItemEditor getEditor() {
        return editor;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public List<PayloadValue> getValues() {
        return Collections.unmodifiableList(values);
    }

    public void addValue(PayloadValue value) {
        if (values.add(value)) {
            value.field = this;
        }
    }

    public boolean removeValue(int index) {
        PayloadValue value = values.remove(index);
        if (value != null) {
            value.field = null;
            return true;
        }

        return false;
    }

    public Attribute getAttribute() {
        Structure structure = editor.getStructure();
        return structure.getAttribute(property.getName());
    }
}
