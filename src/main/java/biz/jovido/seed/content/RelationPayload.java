package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
public class RelationPayload extends Payload {

    @OneToOne(optional = false)
    private Relation relation = new Relation(this);

    @Override
    public Relation getValue() {
        return relation;
    }

    @Override
    public void setValue(Object value) {
        relation = (Relation) value;

        relation.payload = this;
    }
}
