package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.ui.source.Source;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author Stephan Grundner
 */
@Deprecated
public abstract class Field<T> implements HasTemplate {

    private final Source source;
    private final String propertyName;

    public Source getSource() {
        return source;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public T getValue() {
        return (T) source.getProperty(propertyName).getValue();
    }

    public void setValue(T value) {
        source.getProperty(propertyName).setValue(value);
    }

    public Field(Source source, String propertyName) {
        this.source = source;
        this.propertyName = propertyName;
    }
}
