package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class BooleanAttribute extends Attribute {

    @Override
    public Payload createPayload() {
        return new BooleanPayload();
    }
}
