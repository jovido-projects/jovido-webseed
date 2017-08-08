package biz.jovido.seed.content;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class RelationChangeListenerSupport {

    private final Relation relation;
    private final Set<RelationChangeListener> listeners = new HashSet<>();

    public boolean addListener(RelationChangeListener listener) {
        return listeners.add(listener);
    }

    public void notifyTargetAdded(Item target) {
        for (RelationChangeListener listener : listeners) {
            listener.targetAdded(new TargetAddedEvent(relation, target));
        }
    }

    public void notifyTargetRemoved(Item removed) {
        for (RelationChangeListener listener : listeners) {
            listener.relationChange(new RelationChangeEvent(relation,
                    null, removed));
        }
    }

    public void notifyTargetMoved(int from, int to) {
        for (RelationChangeListener listener : listeners) {
            listener.targetMoved(new TargetMovedEvent(relation,
                    from, to));
        }
    }

    public RelationChangeListenerSupport(Relation relation) {
        this.relation = relation;
    }
}
