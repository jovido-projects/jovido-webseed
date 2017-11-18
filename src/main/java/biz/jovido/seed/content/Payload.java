package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payload extends AbstractUnique {

    @Column(insertable = false, updatable = false)
    private String type;

    @ManyToOne
    private Item owningItem;

    private String attributeName;

    private int ordinal = -1;

    public String getType() {
        return type;
    }

    /*public*/ void setType(String type) {
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

    public Payload copy() {
        throw new UnsupportedOperationException();
    }
}
