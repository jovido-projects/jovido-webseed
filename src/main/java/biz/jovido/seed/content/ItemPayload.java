package biz.jovido.seed.content;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class ItemPayload extends Payload {

    @OneToOne(mappedBy = "referrer", cascade = CascadeType.ALL)
    private Item item;

    @Override
    public Item getValue() {
        return item;
    }

    @Override
    public void setValue(Object value) {
        item = (Item) value;

        item.setReferrer(this);
    }
}
