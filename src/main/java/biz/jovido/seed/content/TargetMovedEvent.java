package biz.jovido.seed.content;

import java.util.EventObject;

/**
 * @author Stephan Grundner
 */
public class TargetMovedEvent extends EventObject {

    private final int from;
    private final int to;

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param relation The relation on which the Event initially occurred.
     * @param from
     * @param to
     *
     * @throws IllegalArgumentException if relation is null.
     */
    public TargetMovedEvent(Relation relation, int from, int to) {
        super(relation);
        this.from = from;
        this.to = to;
    }

    public Relation getRelation() {
        return (Relation) source;
    }
}
