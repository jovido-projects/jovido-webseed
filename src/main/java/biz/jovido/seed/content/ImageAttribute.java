package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class ImageAttribute extends AssetAttribute {

    @Override
    public Payload createPayload() {
        return new ImagePayload();
    }
}
