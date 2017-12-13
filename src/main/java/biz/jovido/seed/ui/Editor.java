package biz.jovido.seed.ui;

import java.util.Map;

/**
 * @author Stephan Grundner
 */
public interface Editor<F extends Form> {

    Map<String, F> getForms();
}
