package biz.jovido.seed.ui.source;

import biz.jovido.seed.util.MapUtils;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractSource implements Source {

    protected abstract static class AbstractProperty<V, S extends Source> implements Property<V> {

        protected final S source;
        protected final String name;
        private boolean readOnly;

        public S getSource() {
            return source;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isReadOnly() {
            return readOnly;
        }

        public void setReadOnly(boolean readOnly) {
            this.readOnly = readOnly;
        }

        protected abstract void setValue(int index, V value);

        @Override
        public V getValue() {
            List<V> values = getValues();
            if (values.size() > 0) {
                return values.get(0);
            }

            return null;
        }

        @Override
        public void setValue(V v) {
            setValue(0, v);
        }

        public AbstractProperty(S source, String name) {
            this.source = source;
            this.name = name;
        }
    }

    private final Map<String, Property> propertyByName = new LinkedHashMap<>();

    @Override
    public Set<String> getPropertyNames() {
        return Collections.unmodifiableSet(propertyByName.keySet());
    }

    @Override
    public Collection<Property> getProperties() {
        return Collections.unmodifiableCollection(propertyByName.values());
    }

    @Override
    public boolean containsProperty(String name) {
        return propertyByName.containsKey(name);
    }

    @Override
    public Property getProperty(String name) {
        return propertyByName.get(name);
    }

    protected void addProperty(String name, Property property) {
        if (containsProperty(name)) {
            throw new IllegalArgumentException(String.format("Property [%s] already exists", name));
        }

//        propertyByName.put(name, property);
        MapUtils.putOnce(propertyByName, name, property);
    }

    @Override
    public boolean removeProperty(String name) {
        return propertyByName.remove(name) != null;
    }
}
