package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorColumn(name = "type")
public abstract class Payload extends AbstractUnique {

    @Column(insertable = false, updatable = false)
    private String type;

    @ManyToOne
    private Item item;

    private String attributeName;

    private int ordinal = -1;

    @Lob
    private String text;

    public String getType() {
        return type;
    }

    /*public*/ void setType(String type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    /*public*/ void setItem(Item item) {
        this.item = item;
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

    protected String getText() {
        return text;
    }

    protected void setText(String text) {
        this.text = text;
    }

    @Deprecated
    public void remove() {
        item.removePayload(this);
    }

    public boolean differsFrom(Payload other) {
        return true;
    }

    public abstract Payload copy();
}
