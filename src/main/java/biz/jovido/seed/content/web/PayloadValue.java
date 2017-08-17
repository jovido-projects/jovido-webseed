package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Payload;
import biz.jovido.seed.web.Value;

/**
 * @author Stephan Grundner
 */
public class PayloadValue implements Value<PropertyField, Payload> {

    PropertyField field;

    private Payload payload;

    public PropertyField getField() {
        return field;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
