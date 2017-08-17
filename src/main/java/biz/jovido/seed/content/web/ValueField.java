package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Item;

/**
 * @author Stephan Grundner
 */
public class ValueField extends Field {

    public Object getValue() {
        Item item = getEditor().getItem();
        return item.getValue(getName());
    }

    public void setValue(Object value) {
        Item item = getEditor().getItem();
        item.setValue(getName(), value);
    }
}
