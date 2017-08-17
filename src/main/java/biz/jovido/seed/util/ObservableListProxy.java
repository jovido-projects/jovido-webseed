package biz.jovido.seed.util;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Stephan Grundner
 */
public class ObservableListProxy<E> extends AbstractObservableCollectionProxy<E, List<E>, ListObserver<E>> implements List<E> {

    protected void notifyAddedAll(int index, Collection<? extends E> c) {
        for (ListObserver<E> observer : observers) {
            observer.addedAll(c);
        }
    }

    protected void notifySet(int index, E e) {
        for (ListObserver<E> observer : observers) {
            observer.set(index, e);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (getObservable().addAll(c)) {
            notifyAddedAll(index, c);
            return true;
        }

        return false;
    }

    protected void notifyAdded(int index, E e) {
        for (ListObserver<E> observer : observers) {
            observer.added(index, e);
        }
    }

    protected void notifyRemoved(int index, E e) {
        for (ListObserver<E> observer : observers) {
            observer.removed(index, e);
        }
    }

    @Override
    public E get(int index) {
        return getObservable().get(index);
    }

    @Override
    public E set(int index, E e) {
        E replaced = getObservable().set(index, e);
        notifySet(index, replaced);
        return replaced;
    }

    @Override
    public void add(int index, E e) {
        getObservable().add(index, e);
        notifyAdded(index, e);
    }

    @Override
    public E remove(int index) {
        E removed = getObservable().remove(index);
        notifyRemoved(index, removed);
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        return getObservable().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return getObservable().lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
//        return getObservable().listIterator();
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
//        return getObservable().listIterator(index);
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return getObservable().subList(fromIndex, toIndex);
    }

    public ObservableListProxy(ObservableProvider<E, List<E>> provider) {
        super(provider);
    }
}
