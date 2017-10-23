package biz.jovido.seed.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public abstract class UnmodifiableMapProxy<K, V> implements Map<K, V> {

    protected abstract Map<K, V> getMap();

    @Override
    public int size() {
        return getMap().size();
    }

    @Override
    public boolean isEmpty() {
        return getMap().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return getMap().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return getMap().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return getMap().get(key);
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(getMap().keySet());
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(getMap().values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(getMap().entrySet());
    }
}
