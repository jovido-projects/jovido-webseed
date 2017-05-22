package biz.jovido.seed.content.payload;

import biz.jovido.seed.content.Asset;
import biz.jovido.seed.content.Payload;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author Stephan Grundner
 */
@Entity
public class AssetPayload extends Payload<Asset> {

    @ManyToOne(cascade = CascadeType.ALL)
    private Asset value;

    @Override
    public Asset getValue() {
        return value;
    }

    @Override
    public void setValue(Asset value) {
        this.value = value;
    }
}
