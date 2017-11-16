package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @Transient
    private Set<PayloadChangeListener> changeListeners = new LinkedHashSet<>();

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

    public boolean addChangeListener(PayloadChangeListener changeListener) {
        return changeListeners.add(changeListener);
    }

    public boolean removeChangeListener(PayloadChangeListener changeListener) {
        return changeListeners.remove(changeListener);
    }

    protected void notifyChanged() {
        for (PayloadChangeListener changeListener : changeListeners) {
            changeListener.payloadChanged(this);
        }
    }

    public void remove() {
        group.removePayload(getOrdinal());
    }

    public boolean differsFrom(Payload other) {
        return true;
    }

    public abstract Payload copy();
}
