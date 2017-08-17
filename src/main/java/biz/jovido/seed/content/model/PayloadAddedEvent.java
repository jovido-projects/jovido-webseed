package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class PayloadAddedEvent extends PropertyChangeEvent {

    public PayloadAddedEvent(Property property, Payload payload) {
        super(property, payload);
    }
}
