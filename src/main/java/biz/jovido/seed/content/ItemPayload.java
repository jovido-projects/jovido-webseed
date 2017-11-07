package biz.jovido.seed.content;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("Item")
public class ItemPayload extends Payload<Item> {

    @OneToOne(mappedBy = "payload", cascade = CascadeType.ALL, orphanRemoval = true)
    private Item item;

    public Item getItem() {
        return item;
    }

    @Override
    public Item getValue() {
        return item;
    }

    @Override
    public void setValue(Item value) {
        item = value;
        item.setPayload(this);
    }
}
