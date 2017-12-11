package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;

import java.util.Map;

/**
 * @author Stephan Grundner
 */
public interface Form extends HasTemplate {

    Map<String, Field<?>> getFields();
    String getNestedPath();

    Binding getBinding();
    void setBinding(Binding binding);
}
