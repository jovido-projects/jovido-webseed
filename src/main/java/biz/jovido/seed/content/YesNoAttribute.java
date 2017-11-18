package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class YesNoAttribute extends Attribute {

    @Override
    public Payload createPayload() {
        return new YesNoPayload();
    }
}
