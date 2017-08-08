package biz.jovido.seed.content;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class ItemChangeListenerSupport {

    private final Item item;
    private final Set<ItemChangeListener> listeners = new HashSet<>();

    public boolean addListener(ItemChangeListener listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(ItemChangeListener listener) {
        return listeners.remove(listener);
    }

    public void notifyPayloadChange(String attributeName) {
        for (ItemChangeListener listener : listeners) {
            listener.payloadChange(new PayloadChangeEvent(item,
                    attributeName));
        }
    }

    public ItemChangeListenerSupport(Item item) {
        this.item = item;
    }
}
