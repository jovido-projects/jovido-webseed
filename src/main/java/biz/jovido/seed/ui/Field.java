package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public class Field<T> implements HasTemplate {

    private SourceProperty property;
    private String bindingPath;
    private String template;

    public SourceProperty getProperty() {
        return property;
    }

    public void setProperty(SourceProperty property) {
        this.property = property;
    }

    public String getBindingPath() {
        return bindingPath;
    }

    public void setBindingPath(String bindingPath) {
        this.bindingPath = bindingPath;
    }

    public List<T> getValues() {
        return (List<T>) property.getValues();
    }

    public T getValue() {
        return (T) property.getValue();
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

    public Field() { }

    public Field(SourceProperty property) {
        this.property = property;
    }
}
