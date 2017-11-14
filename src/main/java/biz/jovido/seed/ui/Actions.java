package biz.jovido.seed.ui;

import org.apache.commons.collections4.list.AbstractListDecorator;

import java.util.ArrayList;

/**
 * @author Stephan Grundner
 */
public class Actions extends AbstractListDecorator<Action> {

    private Text text = new Text();

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Actions() {
        super(new ArrayList<>());
    }
}
