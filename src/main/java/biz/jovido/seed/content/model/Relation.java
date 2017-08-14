package biz.jovido.seed.content.model;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
public final class Relation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "relation")
    RelationPayload source;

    @ManyToOne
    Item target;

    public Long getId() {
        return id;
    }

    public RelationPayload getSource() {
        return source;
    }

    public Item getTarget() {
        return target;
    }
}
