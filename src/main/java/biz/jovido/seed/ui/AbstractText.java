package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractText implements Text, HasTemplate {

    private String template;

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
