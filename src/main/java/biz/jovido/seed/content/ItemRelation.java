package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("Item")
public class ItemRelation extends Relation<Item> {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Item item;

    @Override
    public Item getTarget() {
        return item;
    }

    @Override
    public void setTarget(Item target) {
        item = target;
    }

    @Override
    public Payload copy() {
        ItemRelation copy = new ItemRelation();

        Item target = getTarget();
        if (target != null) {
            copy.setTarget(target.copy());
        }

        return copy;
    }
}
