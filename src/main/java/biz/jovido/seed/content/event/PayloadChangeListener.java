package biz.jovido.seed.content.event;

import java.util.EventListener;

/**
 * @author Stephan Grundner
 */
public interface PayloadChangeListener extends EventListener {

    void payloadChanged(PayloadChange change);
}
