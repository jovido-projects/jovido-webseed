package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;

/**
 * @author Stephan Grundner
 */
public class TextField extends Field<String> implements HasTemplate {

    private String template = "ui/field/text";

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public TextField(Source.Property<String> property) {
        super(property);
    }
}
