package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class IconAttribute extends Attribute {

    @Override
    public Payload createPayload() {
        return new IconPayload();
    }
}
