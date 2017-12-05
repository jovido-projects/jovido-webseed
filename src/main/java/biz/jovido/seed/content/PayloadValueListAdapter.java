package biz.jovido.seed.content;

import biz.jovido.seed.content.Payload;
import biz.jovido.seed.content.PayloadList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Stephan Grundner
 */
public class PayloadValueListAdapter<T, P extends Payload<T>> implements List<T> {

    private final PayloadList<P> list;

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    protected static class PayloadValueIterator<T, P extends Payload<T>> implements Iterator<T> {

        private final Iterator<P> payloadIterator;

        @Override
        public boolean hasNext() {
            return payloadIterator.hasNext();
        }

        @Override
        public T next() {
            return payloadIterator.next().getValue();
        }

        public PayloadValueIterator(Iterator<P> payloadIterator) {
            this.payloadIterator = payloadIterator;
        }
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<P> payloadIterator = list.iterator();
        return new PayloadValueIterator<>(payloadIterator);
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        P payload = list.get(index);
        return payload.getValue();
    }

    @Override
    public T set(int index, T element) {
        P payload = list.get(index);
        T tmp = payload.getValue();
        payload.setValue(element);
        return tmp;
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public PayloadValueListAdapter(PayloadList<P> list) {
        this.list = list;
    }
}
