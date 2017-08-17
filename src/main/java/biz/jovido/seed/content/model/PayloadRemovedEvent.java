package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class PayloadRemovedEvent extends PropertyChangeEvent {

    private final int index;

    public int getIndex() {
        return index;
    }

    public PayloadRemovedEvent(Property property, Payload payload, int index) {
        super(property, payload);
        this.index = index;
    }
}
