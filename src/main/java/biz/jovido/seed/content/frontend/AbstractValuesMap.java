package biz.jovido.seed.content.frontend;

import org.apache.commons.collections4.map.AbstractMapDecorator;

import java.util.HashMap;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractValuesMap<V> extends AbstractMapDecorator<String, V> implements Values {

    protected AbstractValuesMap() {
        super(new HashMap<>());
    }
}
