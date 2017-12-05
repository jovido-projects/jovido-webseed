package biz.jovido.seed.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Stephan Grundner
 */
public abstract class CollectionDecorator<E> implements Collection<E> {

    protected abstract Collection<E> decorated();

    @Override
    public int size() {
        return decorated().size();
    }

    @Override
    public boolean isEmpty() {
        return decorated().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return decorated().contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return decorated().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return decorated().toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return decorated().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = true;
        for (E e : c) {
            result &= add(e);
        }

        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = true;
        for (Object o : c) {
            result &= remove(o);
        }

        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E e = it.next();
            remove(e);
        }
    }
}
