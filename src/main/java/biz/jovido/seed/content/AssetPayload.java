package biz.jovido.seed.content;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class AssetPayload extends Payload {

    @ManyToOne
    private Asset asset;

    @Override
    public Asset getValue() {
        return asset;
    }

    @Override
    public void setValue(Object value) {
        asset = (Asset) value;
    }
}
