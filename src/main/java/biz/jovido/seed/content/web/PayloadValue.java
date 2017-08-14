package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Payload;

/**
 * @author Stephan Grundner
 */
public class PayloadValue extends Value {

    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public PayloadValue() {
        super(Type.PAYLOAD);
    }
}
