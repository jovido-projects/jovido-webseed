package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class RelationPayload extends Payload {

    @OneToOne
    private Relation relation;

    @Override
    public Relation getValue() {
        return relation;
    }

    @Override
    public void setValue(Object value) {
        relation = (Relation) value;

        relation.source = this;
    }
}
