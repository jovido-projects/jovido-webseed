package biz.jovido.seed.content;

import java.util.EventObject;

/**
 * @author Stephan Grundner
 */
public abstract class ItemChangeEvent extends EventObject {

    /**
     * Constructs a new {@code PayloadChangeEvent}.
     *
     * @param item The item that fired the event.
     * @throws IllegalArgumentException if {@code source} is null.
     */
    public ItemChangeEvent(Item item) {
        super(item);
    }
}
