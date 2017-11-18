package biz.jovido.seed.content;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("item")
public class ItemPayload extends Payload {

    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public Payload copy() {
        ItemPayload copy = new ItemPayload();
        copy.setAttributeName(getAttributeName());

        Item item = getItem();
        if (item != null) {
            copy.setItem(item.copy());
        }

        return copy;
    }

    public ItemPayload() {
        super(PayloadType.ITEM);
    }
}
