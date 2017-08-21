package biz.jovido.seed.content.model;

import biz.jovido.seed.util.ObservableListProxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

//    @ManyToOne
//    Item target;
    @ManyToMany(cascade = CascadeType.ALL)
    private final List<Item> targets = new ArrayList<>();

    @Transient
    private final ObservableListProxy<Item> targetsObservation =
            new ObservableListProxy<>(() -> targets);

    public Long getId() {
        return id;
    }

    public RelationPayload getSource() {
        return source;
    }

    public ObservableListProxy<Item> getTargets() {
        return targetsObservation;
    }

    @Deprecated
    public void addTarget(Item target) {
        if (getTargets().add(target)) {
            target.addRelation(this);
        }
    }
}
