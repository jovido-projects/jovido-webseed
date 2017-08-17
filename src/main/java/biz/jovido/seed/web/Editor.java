package biz.jovido.seed.web;

import java.util.Map;

/**
 * @author Stephan Grundner
 */
public interface Editor<F extends Field> {

    Map<String, F> getFields();

    F getField(String name);
    void setField(String name, F field);
}
