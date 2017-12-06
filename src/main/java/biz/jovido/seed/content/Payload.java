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

    @ManyToOne(optional = false)
    private PayloadList<T> list;

    private int ordinal;

    public PayloadList<T> getList() {
        return list;
    }

    protected void setList(PayloadList<T> list) {
        this.list = list;
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
