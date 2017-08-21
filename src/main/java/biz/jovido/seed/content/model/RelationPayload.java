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
    private Relation relation;

    @Override
    public Relation getValue() {
        return relation;
    }

    @Override
    public void setValue(Object data) {
        relation = (Relation) data;

        relation.source = this;
    }
}
