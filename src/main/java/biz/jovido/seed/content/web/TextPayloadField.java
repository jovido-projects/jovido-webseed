package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.TextPayload;

/**
 * @author Stephan Grundner
 */
public class TextPayloadField extends PayloadField<TextPayload> {

    @Override
    public Object getValue() {
        return getPayload().getValue();
    }

    @Override
    public void setValue(Object value) {
        getPayload().setValue(value);
    }
}
