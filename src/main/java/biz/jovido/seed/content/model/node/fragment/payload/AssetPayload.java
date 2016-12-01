package biz.jovido.seed.content.model.node.fragment.payload;

import biz.jovido.seed.content.model.Asset;
import biz.jovido.seed.content.model.node.fragment.Payload;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue(Payload.Type.ASSET)
@Entity
public class AssetPayload extends Payload<Asset> {

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset value;

    public Asset getValue() {
        return value;
    }

    public void setValue(Asset value) {
        this.value = value;
    }
}
