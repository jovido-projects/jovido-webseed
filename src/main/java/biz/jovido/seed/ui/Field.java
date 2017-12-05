package biz.jovido.seed.ui;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public abstract class Field<T> {

    private Source.Property<T> property;
    private String bindingPath;

    public Source.Property<T> getProperty() {
        return property;
    }

    public void setProperty(Source.Property<T> property) {
        this.property = property;
    }

    public String getBindingPath() {
        return bindingPath;
    }

    public void setBindingPath(String bindingPath) {
        this.bindingPath = bindingPath;
    }

    public List<T> getValues() {
        return property.getValues();
    }

    public T getValue() {
        return property.getValue();
    }

    public void setValue(T value) {
        property.setValue(value);
    }

    public Field(Source.Property<T> property) {
        this.property = property;
    }
}
