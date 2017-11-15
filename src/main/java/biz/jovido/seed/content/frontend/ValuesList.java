package biz.jovido.seed.content.frontend;

import biz.jovido.seed.content.PayloadGroup;
import org.apache.commons.collections4.list.AbstractListDecorator;

import java.util.ArrayList;

/**
 * @author Stephan Grundner
 */
public final class ValuesList extends AbstractListDecorator<Values> implements Values {

    private final PayloadGroup payloadGroup;

    public PayloadGroup getPayloadGroup() {
        return payloadGroup;
    }

//    @Override
//    public int size() {
//        return Integer.MAX_VALUE;
//    }

    public ValuesList(PayloadGroup payloadGroup) {
//        super(LazyList.lazyList(new ArrayList<>(), () -> null));
        super(new ArrayList<>());
        this.payloadGroup = payloadGroup;
    }
}
