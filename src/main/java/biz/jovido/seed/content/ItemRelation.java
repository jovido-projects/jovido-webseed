package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("Item")
public class ItemRelation extends Relation<Item> {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "target_item_id")
    private Item targetItem;

    @Override
    public Item getTarget() {
        return targetItem;
    }

    @Override
    public void setTarget(Item target) {
        targetItem = target;
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
