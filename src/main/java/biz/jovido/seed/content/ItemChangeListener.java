package biz.jovido.seed.content;

import java.util.EventListener;

/**
 * @author Stephan Grundner
 */
public interface ItemChangeListener extends EventListener {

    void payloadChange(PayloadChangeEvent event);
}
