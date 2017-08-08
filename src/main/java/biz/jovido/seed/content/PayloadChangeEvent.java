package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class PayloadChangeEvent extends ItemChangeEvent {

    private final String attributeName;

    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Constructs a new {@code PayloadChangeEvent}.
     *
     * @param item          The item that fired the event.
     * @param attributeName The name of the attribute.
     * @throws IllegalArgumentException if {@code source} is null.
     */
    public PayloadChangeEvent(Item item, String attributeName) {
        super(item);

        this.attributeName = attributeName;
    }
}
