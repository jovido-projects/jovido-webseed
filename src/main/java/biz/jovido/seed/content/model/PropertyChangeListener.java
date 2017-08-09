package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public interface PropertyChangeListener {

    void valueSet();
    void valueAdded();
    void valueMoved();
}
