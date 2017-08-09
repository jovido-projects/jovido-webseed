package biz.jovido.seed.content.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class PropertyChangeSupport {

    private final Set<PropertyChangeListener> listeners = new HashSet<>();

    public boolean addListener(PropertyChangeListener listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(PropertyChangeListener listener) {
        return listeners.remove(listener);
    }

    public void notifyValueAdded() {

    }
}
