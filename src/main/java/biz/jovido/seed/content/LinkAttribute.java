package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
//@Entity
public class LinkAttribute extends Attribute {

    @Override
    public Payload createPayload() {
        return new Link();
    }
}
