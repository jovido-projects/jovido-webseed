package biz.jovido.seed.content.event;

import java.util.EventListener;

/**
 * @author Stephan Grundner
 */
public interface FragmentChangeListener extends EventListener {

    void fragmentChanged(FragmentChange change);
}
