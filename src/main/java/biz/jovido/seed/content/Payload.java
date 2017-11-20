package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payload extends AbstractUnique {

    @Enumerated(EnumType.STRING)
    private PayloadType type;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PayloadGroup group;

    private int ordinal = -1;

    public PayloadType getType() {
        return type;
    }

    /*public*/ void setType(PayloadType type) {
        this.type = type;
    }

    public PayloadGroup getGroup() {
        return group;
    }

    /*public*/ void setGroup(PayloadGroup group) {
        this.group = group;
    }

    public int getOrdinal() {
        return ordinal;
    }

    /*public*/ void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public abstract Payload copy();

    protected Payload(PayloadType type) {
        this.type = type;
    }

    public Payload() {}
}
