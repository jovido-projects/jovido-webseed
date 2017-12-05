package biz.jovido.seed.util;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Stephan Grundner
 */
public abstract class ListDecorator<E> extends CollectionDecorator<E> implements List<E> {

    @Override
    protected abstract List<E> decorated();

    @Override
    public boolean add(E e) {
        add(size(), e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        return decorated().get(index);
    }

    @Override
    public int indexOf(Object o) {
        return decorated().indexOf(o);
    }

    @Override
    public boolean remove(Object o) {
        remove(indexOf(o));
        return true;
    }

    @Override
    public int lastIndexOf(Object o) {
        return decorated().lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
