package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind")
public abstract class Payload<T> extends AbstractUnique {

    @ManyToOne(targetEntity = Payload.class, optional = false)
    private PayloadList<? extends Payload<T>> list;

    private int ordinal;

    public PayloadList<? extends Payload<T>> getList() {
        return list;
    }

    public void setList(PayloadList<? extends Payload<?>> list) {
        this.list = (PayloadList<? extends Payload<T>>) list;
    }

    public int getOrdinal() {
        return ordinal;
    }

    protected void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public abstract T getValue();
    public abstract void setValue(T value);
}
