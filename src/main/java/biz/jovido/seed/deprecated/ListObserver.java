package biz.jovido.seed.deprecated;

import java.util.Collection;

/**
 * @author Stephan Grundner
 */
public interface ListObserver<E> extends CollectionObserver<E> {

    void addedAll(int index, Collection<? extends E> c);
    void set(int index, E replaced);
    void added(int index, E e);
    void removed(int index, E e);
}
