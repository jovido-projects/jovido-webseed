package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class ImagePayloadAttribute extends AssetPayloadAttribute<Image> {

    @Override
    public Payload<Image> createPayload() {
        return new ImagePayload();
    }
}
