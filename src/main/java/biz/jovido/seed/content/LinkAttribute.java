package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class LinkAttribute extends Attribute {

    @Override
    public Payload createPayload() {
        return new LinkPayload();
    }
}
