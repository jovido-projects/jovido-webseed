package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;
import org.apache.commons.collections4.list.AbstractListDecorator;

import java.util.ArrayList;

/**
 * @author Stephan Grundner
 */
public class Actions extends AbstractListDecorator<Action> implements HasTemplate {

    private String template = "ui/actions";
    private Text text;
    private Action defaultAction;

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Action getDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(Action defaultAction) {
        this.defaultAction = defaultAction;
    }

    public Actions() {
        super(new ArrayList<>());
    }
}
