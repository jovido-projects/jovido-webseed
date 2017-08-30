package biz.jovido.seed.content.model;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind")
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"sequence_id", "ordinal"}))
public abstract class Payload {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sequence_id")
    private Sequence sequence;

    private int ordinal;

    public Long getId() {
        return id;
    }

    /*default*/ void setId(Long id) {
        this.id = id;
    }

    public Sequence getSequence() {
        return sequence;
    }

    /*default*/ void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public int getOrdinal() {
        return ordinal;
    }

    /*default*/ void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

//    public abstract Object getValue();
//    public abstract void setValue(Object value);
}
