package biz.jovido.seed.content.frontend;

/**
 * @author Stephan Grundner
 */
public interface ValuesFactory<S> {

    Values valuesFor(S source);
}
