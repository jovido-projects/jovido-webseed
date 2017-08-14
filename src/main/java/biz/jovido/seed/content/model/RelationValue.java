package biz.jovido.seed.content.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class RelationValue extends Value {

    @OneToOne(optional = false)
    private Relation relation;

    @Override
    public Relation getData() {
        return relation;
    }

    @Override
    public void setData(Object data) {
        relation = (Relation) data;
    }
}
