package biz.jovido.seed.content;

import javax.persistence.MappedSuperclass;

/**
 * @author Stephan Grundner
 */
@MappedSuperclass
public abstract class ValuePayload<T> extends Payload<T> {

    public abstract T getValue();
    public abstract void setValue(T value);
}
