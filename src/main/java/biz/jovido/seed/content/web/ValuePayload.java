package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Value;

/**
 * @author Stephan Grundner
 */
public class ValuePayload extends Payload {

    private Value value;

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public ValuePayload() {
        super(Type.VALUE);
    }
}
