package biz.jovido.seed;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractListProxy<T> extends AbstractCollectionProxy<T> implements List<T> {

    @Override
    protected abstract List<T> getDelegate();

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return getDelegate().addAll(index, c);
    }

    @Override
    public T get(int index) {
        return getDelegate().get(index);
    }

    @Override
    public T set(int index, T element) {
        return getDelegate().set(index, element);
    }

    @Override
    public void add(int index, T element) {
        getDelegate().add(index, element);
    }

    @Override
    public T remove(int index) {
        return getDelegate().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return getDelegate().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return getDelegate().lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return getDelegate().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return getDelegate().listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return getDelegate().subList(fromIndex, toIndex);
    }
}
