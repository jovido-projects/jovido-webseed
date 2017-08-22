package biz.jovido.seed.deprecated;

import java.util.Collection;

/**
 * @author Stephan Grundner
 */
public interface ObservableProvider<E, T extends Collection<E>> {

    T getObservable();
}
