package biz.jovido.seed.content.frontend;

import org.apache.commons.collections4.list.AbstractListDecorator;

import java.util.ArrayList;

/**
 * @author Stephan Grundner
 */
public final class ValuesList extends AbstractListDecorator<Values> implements Values {

    private final String attributeName;

    public String getAttributeName() {
        return attributeName;
    }

    public ValuesList(String attributeName) {
//        super(LazyList.lazyList(new ArrayList<>(), () -> null));
        super(new ArrayList<>());
        this.attributeName = attributeName;
    }
}
