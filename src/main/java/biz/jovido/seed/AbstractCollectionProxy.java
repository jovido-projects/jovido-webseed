package biz.jovido.seed;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractCollectionProxy<T> implements Collection<T> {

    protected abstract Collection<T> getDelegate();

    @Override
    public int size() {
        return getDelegate().size();
    }

    @Override
    public boolean isEmpty() {
        return getDelegate().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getDelegate().contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return getDelegate().iterator();
    }

    @Override
    public Object[] toArray() {
        return getDelegate().toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return getDelegate().toArray(a);
    }

    @Override
    public boolean add(T t) {
        return getDelegate().add(t);
    }

    @Override
    public boolean remove(Object o) {
        return getDelegate().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getDelegate().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return getDelegate().addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return getDelegate().removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return getDelegate().retainAll(c);
    }

    @Override
    public void clear() {
        getDelegate().clear();
    }
}
