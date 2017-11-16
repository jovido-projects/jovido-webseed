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

    @ManyToOne(targetEntity = PayloadGroup.class)
    private PayloadGroup group;

    private int ordinal = -1;

    @Lob
    private String text;

    public String getType() {
        return type;
    }

    /*public*/ void setType(String type) {
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

    protected String getText() {
        return text;
    }

    protected void setText(String text) {
        this.text = text;
    }

    public void remove() {
        group.removePayload(getOrdinal());
    }

    public boolean differsFrom(Payload other) {
        return true;
    }

    public abstract Payload copy();
}
