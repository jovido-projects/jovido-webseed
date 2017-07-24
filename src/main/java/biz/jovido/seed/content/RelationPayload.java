package biz.jovido.seed.content;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class RelationPayload extends Payload {

    @OneToOne(mappedBy = "payload", cascade = CascadeType.ALL)
    private Relation relation;

    @Override
    public Relation getValue() {
        return relation;
    }

    @Override
    public void setValue(Object value) {
        relation = (Relation) value;
    }
}
