package biz.jovido.seed.ui;

import org.apache.commons.collections4.list.AbstractListDecorator;

import java.util.ArrayList;

/**
 * @author Stephan Grundner
 */
public class Actions extends AbstractListDecorator<Action> {

    private Action primary;

    public Action getPrimary() {
        return primary;
    }

    public void setPrimary(Action primary) {
        this.primary = primary;
    }

    public Actions() {
        super(new ArrayList<>());
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && primary == null;
    }
}
