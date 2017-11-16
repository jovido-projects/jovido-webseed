package biz.jovido.seed.uimodel;

/**
 * @author Stephan Grundner
 */
public interface InvalidationListener<T> {

    void invalidated(T t);
}
