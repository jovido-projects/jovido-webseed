package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class Relation<T> extends Payload {

    public abstract T getTarget();
    public abstract void setTarget(T target);
}
