package biz.jovido.seed.content.event;

import biz.jovido.seed.content.Payload;

/**
 * @author Stephan Grundner
 */
public abstract class PayloadChange {

    private final Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public PayloadChange(Payload payload) {
        this.payload = payload;
    }
}
