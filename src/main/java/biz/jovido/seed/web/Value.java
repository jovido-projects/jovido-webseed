package biz.jovido.seed.web;

/**
 * @author Stephan Grundner
 */
public interface Value<F extends Field, P> {

    F getField();

    P getPayload();

    void setPayload(P payload);
}
