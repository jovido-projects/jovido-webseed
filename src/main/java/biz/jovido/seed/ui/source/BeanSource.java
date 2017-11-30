package biz.jovido.seed.ui.source;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * @author Stephan Grundner
 */
public class BeanSource implements Source {

    private final SourcesContainer container;
    private final Object bean;
    protected final BeanWrapper wrapper;

    private final Map<String, BeanSourceProperty> propertyByName = new LinkedHashMap<>();

    public Object getBean() {
        return bean;
    }

    @Override
    public Set<String> getPropertyNames() {
        return Collections.unmodifiableSet(propertyByName.keySet());
    }

    @Override
    public Collection<SourceProperty> getProperties() {
        return Collections.unmodifiableCollection(propertyByName.values());
    }

    @Override
    public SourceProperty getProperty(String name) {
        SourceProperty property = propertyByName.get(name);
        if (property == null) {
            PropertyDescriptor descriptor = wrapper.getPropertyDescriptor(name);
            if (descriptor != null) {
                property = addProperty(name);
            }
        }

        return property;
    }

    public BeanSourceProperty addProperty(String name) {
        if (propertyByName.containsKey(name)) {
            throw new IllegalArgumentException(String.format("Property [%s] already exists", name));
        }
        BeanSourceProperty property = new BeanSourceProperty(this, name);
        propertyByName.put(name, property);
        return property;
    }



    public BeanSource(SourcesContainer container, Object bean) {
        this.container = container;
        this.bean = bean;
        wrapper = new BeanWrapperImpl(bean);
    }

    public BeanSource(Object bean) {
        this(null, bean);
    }
}
