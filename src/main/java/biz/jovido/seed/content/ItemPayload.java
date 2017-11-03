package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("Item")
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

    @Override
    public void setValue(Item value) {
        item = value;
    }
}
