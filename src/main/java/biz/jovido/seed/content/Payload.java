package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;
import biz.jovido.seed.content.asset.Asset;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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
}
