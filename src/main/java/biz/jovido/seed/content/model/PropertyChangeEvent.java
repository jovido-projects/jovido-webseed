package biz.jovido.seed.content.model;

import java.util.EventObject;

/**
 * @author Stephan Grundner
 */
public abstract class PropertyChangeEvent extends EventObject {

    private final Payload payload;

    /**
     * Constructs a prototypical Event.
     *
     * @param property The property on which the Event initially occurred.
     * @throws IllegalArgumentException if property is null.
     */
    public PropertyChangeEvent(Property property, Payload payload) {
        super(property);
        this.payload = payload;
    }

    public Property getProperty() {
        return (Property) getSource();
    }

    public Payload getPayload() {
        return payload;
    }
}
