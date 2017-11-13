package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
//@Entity
public class ImageAttribute extends AssetAttribute {

    @Override
    public Payload createPayload() {
        return new ImageRelation();
    }
}
