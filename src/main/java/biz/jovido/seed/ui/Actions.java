package biz.jovido.seed.ui;

import org.apache.commons.collections4.map.AbstractMapDecorator;

import java.util.LinkedHashMap;

/**
 * @author Stephan Grundner
 */
public class Actions extends AbstractMapDecorator<String, Action> {

    public Actions() {
        super(new LinkedHashMap<>());
    }
}
