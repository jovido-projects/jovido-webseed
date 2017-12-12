package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;

/**
 * @author Stephan Grundner
 */
public class Field implements HasTemplate {

    private String template;
    private ValueAccessor valueAccessor;

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public ValueAccessor getValueAccessor() {
        return valueAccessor;
    }

    public void setValueAccessor(ValueAccessor valueAccessor) {
        this.valueAccessor = valueAccessor;
    }

    public Object getValue() {
        return valueAccessor.getValue();
    }

    public void setValue(Object value) {
        valueAccessor.setValue(value);
    }

    public Field() { }
}
