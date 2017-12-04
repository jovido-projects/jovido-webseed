package biz.jovido.seed.ui.source;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class BeanSource<T> extends AbstractSource {

    public static class Property<V> extends AbstractProperty<V, BeanSource> {

        @Override
        protected void setValue(int index, V value) {

        }

        @Override
        public List<V> getValues() {
            return null;
        }

        @Override
        public V removeValue(int index) {
            return null;
        }

        @Override
        public V getValue() {
            String propertyPath = String.format("bean.%s", name);
            return source.beanWrapper.getPropertyValue(propertyPath);
        }

        @Override
        public void setValue(Object value) {
            String propertyPath = String.format("bean.%s", name);
            source.beanWrapper.setPropertyValue(propertyPath, value);
        }

        public Property(BeanSource source, String name) {
            super(source, name);
        }
    }

    public static Object getBean(Source source) {
        if (source instanceof BeanSource) {
            return ((BeanSource) source).bean;
        }

        return null;
    }

    private final T bean;

    private final BeanWrapper beanWrapper = new BeanWrapperImpl(this);

    public T getBean() {
        return bean;
    }

    @Override
    public Source.Property getProperty(String name) {
        Source.Property property = super.getProperty(name);
        if (property == null) {
            String propertyPath = String.format("bean.%s", name);
            try {
                PropertyDescriptor descriptor = beanWrapper.getPropertyDescriptor(propertyPath);
                if (descriptor != null) {
                    property = new Property(this, name);
                    addProperty(name, property);
                }
            } catch (InvalidPropertyException e) {}
        }

        return property;
    }

    @Override
    public Property addProperty(String name) {
        throw new UnsupportedOperationException();
    }

    public BeanSource(T bean) {
        this.bean = bean;
    }
}
