package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Payload;

/**
 * @author Stephan Grundner
 */
public abstract class PayloadField<P extends Payload> {

    PropertyEditor editor;
    P payload;

    public P getPayload() {
        return payload;
    }

    public int getOrdinal() {
        return getPayload().getOrdinal();
    }

    public abstract Object getValue();
    public abstract void setValue(Object value);
}
