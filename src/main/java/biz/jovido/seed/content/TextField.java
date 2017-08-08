package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class TextField extends Field {

    @Override
    public Object getValue() {
        TextPayload payload = (TextPayload) getPayload();
        return payload.getValue();
    }

    @Override
    public void setValue(Object value) {
        TextPayload payload = (TextPayload) getPayload();
        payload.setValue(value);
    }
}
