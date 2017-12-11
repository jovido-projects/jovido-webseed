package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class Field<T> implements Invalidatable, HasTemplate {

    private BindingProperty<T> property;
    private String bindingPath;
    private String template;
//    private final List<Action> valueActions

    private final Set<InvalidationListener<Field<T>>> invalidationListeners = new LinkedHashSet<>();

    public BindingProperty getProperty() {
        return property;
    }

    public void setProperty(BindingProperty<T> property) {
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

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public boolean addInvalidationListener(InvalidationListener<Field<T>> invalidationListener) {
        return invalidationListeners.add(invalidationListener);
    }

    @Override
    public void invalidate() {
        for (InvalidationListener<Field<T>> invalidationListener : invalidationListeners) {
            invalidationListener.invalidate(this);
        }
    }

    public Field(BindingProperty<T> property) {
        this.property = property;
    }

    public Field() { }
}
