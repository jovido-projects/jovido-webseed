package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class ImagePayloadAttribute extends AssetPayloadAttribute<ImagePayload> {

    @Override
    public ImagePayload createPayload() {
        return new ImagePayload();
    }
}
