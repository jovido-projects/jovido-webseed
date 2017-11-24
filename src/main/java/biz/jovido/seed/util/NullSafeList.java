package biz.jovido.seed.util;

import org.apache.commons.collections4.list.AbstractListDecorator;
import org.apache.commons.collections4.list.LazyList;

import java.util.ArrayList;

/**
 * @author Stephan Grundner
 */
public class NullSafeList<T> extends AbstractListDecorator<T> {

    @Override
    public int size() {
        return Integer.MAX_VALUE;
    }

    public int getLength() {
        return super.size();
    }

    public NullSafeList() {
        super(LazyList.lazyList(new ArrayList<>(), () -> null));
    }
}
