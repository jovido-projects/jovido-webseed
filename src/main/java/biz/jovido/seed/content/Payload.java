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

    @ManyToOne
    private Item owningItem;

    private String attributeName;

    private int ordinal = -1;

    public PayloadType getType() {
        return type;
    }

    /*public*/ void setType(PayloadType type) {
        this.type = type;
    }

    public Item getOwningItem() {
        return owningItem;
    }

    /*public*/ void setOwningItem(Item owningItem) {
        this.owningItem = owningItem;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getOrdinal() {
        return ordinal;
    }

    /*public*/ void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    @Deprecated
    public void remove() {
        owningItem.removePayload(this);
    }

    public abstract Payload copy();

    protected Payload(PayloadType type) {
        this.type = type;
    }

    public Payload() {}
}
