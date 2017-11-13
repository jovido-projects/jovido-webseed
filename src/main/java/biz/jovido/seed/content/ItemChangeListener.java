package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface ItemChangeListener {

    void payloadAdded(Item item, Payload payload);
    void payloadRemoved(Item item, Payload payload);
}
