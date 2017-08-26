package biz.jovido.seed.content.model;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
public class Relation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private RelationPayload source;

    @ManyToOne(cascade = CascadeType.ALL)
    private Item target;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationPayload getSource() {
        return source;
    }

    /*default*/ void setSource(RelationPayload source) {
        this.source = source;
    }

    public Item getTarget() {
        return target;
    }

    public void setTarget(Item target) {
        this.target = target;
    }
}
