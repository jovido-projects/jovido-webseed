package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind")
public abstract class Payload extends AbstractUnique {

    @ManyToOne(optional = false)
    private PayloadSequence sequence;

    private int ordinal;

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
}
