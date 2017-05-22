package biz.jovido.seed;

import java.util.Map;

/**
 * @author Stephan Grundner
 */
public interface ViewState<F extends Form> {

    Map<String, F> getForms();
}
