package biz.jovido.seed.uimodel;

import org.apache.commons.collections4.list.AbstractListDecorator;

import java.util.ArrayList;

/**
 * @author Stephan Grundner
 */
public class Actions extends AbstractListDecorator<Action> {

    private Text text;
    private Action primaryAction;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Action getPrimaryAction() {
        return primaryAction;
    }

    public void setPrimaryAction(Action primaryAction) {
        this.primaryAction = primaryAction;
    }

    public Actions() {
        super(new ArrayList<>());
    }
}
