package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Relation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "relation")
    RelationPayload payload;

    @Transient
    private final RelationChangeListenerSupport changeListenerSupport = new RelationChangeListenerSupport(this);

    @OneToMany
    @JoinTable(name = "relation_target",
            joinColumns = @JoinColumn(name = "relation_id"),
            inverseJoinColumns = @JoinColumn(name = "target_id"))
    private final List<Item> targets = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public RelationPayload getPayload() {
        return payload;
    }

    public List<Item> getTargets() {
        return Collections.unmodifiableList(targets);
    }

    public boolean addTarget(Item target) {
        if (targets.add(target)) {
            target.relations.add(this);
            changeListenerSupport.notifyTargetAdded(target);
            return true;
        }

        return false;
    }

    public boolean removeTarget(Item target) {
        if (targets.remove(target)) {
            target.relations.remove(target);
            changeListenerSupport.notifyTargetRemoved(target);
            return true;
        }

        return false;
    }

    public void moveTarget(int from, int to) {
        Collections.swap(targets, from, to);
    }

    public boolean addChangeListener(RelationChangeListener listener) {
        return changeListenerSupport.addListener(listener);
    }

    public Relation() {}

    public Relation(RelationPayload payload) {
        this.payload = payload;
    }
}
