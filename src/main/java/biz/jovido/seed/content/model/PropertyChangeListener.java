package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public interface PropertyChangeListener {

    void payloadAdded(PayloadAddedEvent event);
    void payloadRemoved(PayloadRemovedEvent event);
    void payloadMoved(PayloadMovedEvent event);
}
