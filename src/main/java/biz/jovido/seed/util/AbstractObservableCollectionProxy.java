package biz.jovido.seed.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractObservableCollectionProxy<E, C extends Collection<E>, O extends CollectionObserver<E>> implements Collection<E> {

    private final ObservableProvider<E, C> provider;
    protected final Set<O> observers = new HashSet<>();

    protected C getObservable() {
        return provider.getObservable();
    }

    public boolean addObserver(O observer) {
        return observers.add(observer);
    }

    public boolean removeObserver(O observer) {
        return observers.remove(observer);
    }

    protected void notifyAdded(E e) {
        for (O observer : observers) {
            observer.added(e);
        }
    }

    protected void notifyAddedAll(Collection<? extends E> c) {
        for (O observer : observers) {
            observer.addedAll(c);
        }
    }

    protected void notifyRemoved(Object o) {
        for (O observer : observers) {
            observer.removed(o);
        }
    }

    protected void notifyRemovedAll(Collection<?> c) {
        for (O observer : observers) {
            observer.removedAll(c);
        }
    }

    protected void notifyCleared() {
        for (O observer : observers) {
            observer.cleared();
        }
    }

    @Override
    public int size() {
        return getObservable().size();
    }

    @Override
    public boolean isEmpty() {
        return getObservable().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getObservable().contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = getObservable().iterator();
        return new Iterator<E>() {
            E e;

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                e = iterator.next();
                return e;
            }

            @Override
            public void remove() {
                iterator.remove();
                notifyRemoved(e);
            }
        };
    }

    @Override
    public Object[] toArray() {
        return getObservable().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return getObservable().toArray(a);
    }

    @Override
    public boolean add(E e) {
        if (getObservable().add(e)) {
            notifyAdded(e);
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (getObservable().remove(o)) {
            notifyRemoved(o);
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getObservable().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (getObservable().addAll(c)) {
            notifyAddedAll(c);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (getObservable().removeAll(c)) {
            notifyRemovedAll(c);
            return true;
        }

        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
//        return getObservable().retainAll(c);
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        getObservable().clear();
        notifyCleared();
    }

    public AbstractObservableCollectionProxy(ObservableProvider<E, C> provider) {
        this.provider = provider;
    }
}
