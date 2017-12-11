package biz.jovido.seed.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class SimpleForm implements Form {

    private final Map<String, Field<?>> fieldByPropertyName = new HashMap<>();
    private String nestedPath;
    private Binding binding;
    private String template;

    @Override
    public Map<String, Field<?>> getFields() {
        return Collections.unmodifiableMap(fieldByPropertyName);
    }

    public <T> void addField(Field<T> field, BindingProperty<T> property) {
        fieldByPropertyName.put(property.getName(), field);
        field.setProperty(property);
    }

    @Override
    public String getNestedPath() {
        return nestedPath;
    }

    public void setNestedPath(String nestedPath) {
        this.nestedPath = nestedPath;
    }

    @Override
    public Binding getBinding() {
        return binding;
    }

    @Override
    public void setBinding(Binding binding) {
        this.binding = binding;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
