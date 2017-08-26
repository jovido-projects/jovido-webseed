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

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;

        relation.setSource(this);
    }
}
