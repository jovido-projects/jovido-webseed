package biz.jovido.seed.content.component;

import biz.jovido.spring.web.ui.MvcComponent;

/**
 * @author Stephan Grundner
 */
@MvcComponent(name = "button", template = "content/component/button")
public class Button {

    private Label label;
    private String url;

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
