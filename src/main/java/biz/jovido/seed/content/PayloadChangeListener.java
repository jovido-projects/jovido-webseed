package biz.jovido.seed.content;

import java.util.EventListener;

/**
 * @author Stephan Grundner
 */
public interface PayloadChangeListener extends EventListener {

    void payloadChanged(Payload payload);
}
