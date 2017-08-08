package biz.jovido.seed.content;

import java.util.EventListener;

/**
 * @author Stephan Grundner
 */
public interface RelationChangeListener extends EventListener {

    void relationChange(RelationChangeEvent event);
    void targetAdded(TargetAddedEvent event);
    void targetMoved(TargetMovedEvent event);
}
