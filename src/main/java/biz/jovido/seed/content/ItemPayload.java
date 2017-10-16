package biz.jovido.seed.content;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class ItemPayload extends Payload<Item> {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public Item getValue() {
        return getItem();
    }
}
