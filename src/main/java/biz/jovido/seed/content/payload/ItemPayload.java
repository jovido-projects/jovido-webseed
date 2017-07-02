package biz.jovido.seed.content.payload;

import biz.jovido.seed.content.Bundle;
import biz.jovido.seed.content.Item;
import biz.jovido.seed.content.Payload;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class ItemPayload extends Payload<Item> {

    @ManyToOne
    private Bundle bundle;

    @Override
    public Item getValue() {
        if (bundle != null) {
            return bundle.getDraft();
        }

        return null;
    }

    @Override
    public void setValue(Item value) {
        if (value != null) {
            bundle = value.getBundle();
        }
    }
}
