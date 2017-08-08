package biz.jovido.seed.content;

import java.util.EventObject;

/**
 * @author Stephan Grundner
 */
public class RelationChangeEvent extends EventObject {

    private final Item added;
    private final Item removed;

    public Item getAdded() {
        return added;
    }

    public Item getRemoved() {
        return removed;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param relation The relation on which the Event initially occurred.
     * @param added
     * @param removed
     *
     * @throws IllegalArgumentException if relation is null.
     */
    public RelationChangeEvent(Relation relation, Item added, Item removed) {
        super(relation);
        this.added = added;
        this.removed = removed;
    }

    public Relation getRelation() {
        return (Relation) source;
    }
}
