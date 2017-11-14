package biz.jovido.seed.ui;

import org.apache.commons.collections4.list.AbstractListDecorator;

import java.util.ArrayList;

/**
 * @author Stephan Grundner
 */
public class Actions extends AbstractListDecorator<Action> {

    public Actions() {
        super(new ArrayList<>());
    }
}
