package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Entity
public class Payload extends AbstractUnique {

    @ManyToOne(targetEntity = Payload.class, optional = false)
    private PayloadSequence sequence;

    private int ordinal = -1;

    @Lob
    private String text;

    private Date date;
    private BigDecimal number;

    @ManyToOne
    private Fragment fragment;

    @ManyToOne
    private Asset asset;

    @Transient
    private final Set<PayloadChangeListener> changeListeners = new LinkedHashSet<>();

    public PayloadSequence getSequence() {
        return sequence;
    }

    protected void setSequence(PayloadSequence sequence) {
        this.sequence = sequence;
    }

    public int getOrdinal() {
        return ordinal;
    }

    protected void setOrdinal(int ordinal) {
        this.ordinal = ordinal;

        notifyPayloadChanged(new PayloadChange.OrdinalSet(this));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public boolean addChangeListener(PayloadChangeListener changeListener) {
        return changeListeners.add(changeListener);
    }

    public boolean removeChangeListener(PayloadChangeListener changeListener) {
        return changeListeners.remove(changeListener);
    }

    protected void notifyPayloadChanged(PayloadChange change) {
        for (PayloadChangeListener changeListener : changeListeners) {
            changeListener.payloadChanged(change);
        }
    }
}
