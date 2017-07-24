package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public abstract class Relation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private RelationPayload payload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationPayload getPayload() {
        return payload;
    }

    public void setPayload(RelationPayload payload) {
        this.payload = payload;
    }
}
