package biz.jovido.seed.deprecated;

import java.util.Collection;

/**
 * @author Stephan Grundner
 */
public interface CollectionObserver<E> {

    void added(E e);
    void addedAll(Collection<? extends E> c);
    void removed(Object o);
    void removedAll(Collection<?> c);
    void cleared();
}
