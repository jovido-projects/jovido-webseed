package biz.jovido.seed.content.model;

import biz.jovido.seed.util.ObservableListProxy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private final List<Item> targets = new ArrayList<>();

//    @Transient
//    private final ObservableListProxy<Item> targetsObservation =
//            new ObservableListProxy<>(() -> targets);

    public Long getId() {
        return id;
    }

    public RelationPayload getSource() {
        return source;
    }

    public List<Item> getTargets() {
        return targets;
    }
}
