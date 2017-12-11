package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public interface InvalidationListener<T extends Invalidatable> {

    void invalidate(T t);
}
