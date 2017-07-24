package biz.jovido.seed.content;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@MappedSuperclass
public abstract class ToOneRelation extends Relation {

    @OneToOne
    private Item target;

    public Item getTarget() {
        return target;
    }

    public void setTarget(Item target) {
        this.target = target;
    }
}
