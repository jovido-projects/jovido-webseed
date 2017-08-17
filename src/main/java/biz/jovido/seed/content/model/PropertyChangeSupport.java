package biz.jovido.seed.content.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class PropertyChangeSupport {

    private final Property property;

    private final Set<PropertyChangeListener> listeners = new HashSet<>();

    public boolean addListener(PropertyChangeListener listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(PropertyChangeListener listener) {
        return listeners.remove(listener);
    }

    public void notifyPayloadAdded(Payload payload) {
        for (PropertyChangeListener listener : listeners) {
            listener.payloadAdded(new PayloadAddedEvent(property, payload));
        }
    }

    public void notifyPayloadRemoved(Payload payload, int index) {
        for (PropertyChangeListener listener : listeners) {
            listener.payloadRemoved(new PayloadRemovedEvent(property, payload, index));
        }
    }

    public void notifyPayloadMoved(Payload payload, int from) {
        for (PropertyChangeListener listener : listeners) {
            listener.payloadMoved(new PayloadMovedEvent(property, payload));
        }
    }

    public PropertyChangeSupport(Property property) {
        this.property = property;
    }
}
