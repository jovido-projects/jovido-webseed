package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class PayloadMovedEvent extends PropertyChangeEvent {

    public PayloadMovedEvent(Property property, Payload payload) {
        super(property, payload);
    }
}
