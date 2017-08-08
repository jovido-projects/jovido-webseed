package biz.jovido.seed.content;

import java.util.EventObject;

/**
 * @author Stephan Grundner
 */
public class TargetAddedEvent extends EventObject {

    private final Item target;

    public Item getTarget() {
        return target;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param relation The relation on which the Event initially occurred.
     * @param target
     *
     * @throws IllegalArgumentException if relation is null.
     */
    public TargetAddedEvent(Relation relation, Item target) {
        super(relation);
        this.target = target;
    }

    public Relation getRelation() {
        return (Relation) source;
    }
}
