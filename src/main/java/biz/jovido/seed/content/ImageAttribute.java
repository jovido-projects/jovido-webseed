package biz.jovido.seed.content;

import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
//@Entity
public class ImageAttribute extends AssetAttribute {

    @Override
    public Payload createPayload() {
        return new ImagePayload();
    }
}
