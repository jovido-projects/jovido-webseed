package biz.jovido.seed.util;

import java.util.Collection;

/**
 * @author Stephan Grundner
 */
public interface ObservableProvider<E, T extends Collection<E>> {

    T getObservable();
}
