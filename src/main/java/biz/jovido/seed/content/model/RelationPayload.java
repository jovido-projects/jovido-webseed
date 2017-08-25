package biz.jovido.seed.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class RelationPayload extends Payload {

    @OneToOne(cascade = CascadeType.ALL)
    private Item item;

    @Override
    public Item getValue() {
        return item;
    }

    @Override
    public void setValue(Object value) {
        item = (Item) value;
//        item.source = this;

        item.relations.add(this);
    }
}
