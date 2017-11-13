package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author Stephan Grundner
 */
public abstract class Field<T> implements HasTemplate {

    private final BeanWrapper wrapper;
    private String bindingPath;
    private String template;

    private boolean disabled;

    public String getBindingPath() {
        return bindingPath;
    }

    public void setBindingPath(String bindingPath) {
        this.bindingPath = bindingPath;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) wrapper.getPropertyValue(bindingPath);
    }

    public void setValue(T value) {
        wrapper.setPropertyValue(bindingPath, value);
    }

    public Field(Object object, String bindingPath) {
        wrapper = new BeanWrapperImpl(object);
        this.bindingPath = bindingPath;
    }
}
