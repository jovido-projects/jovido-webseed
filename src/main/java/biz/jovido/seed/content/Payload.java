package biz.jovido.seed.content;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public abstract class Payload<T> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Sequence.class)
    private Sequence<T> sequence;

    private int ordinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sequence<T> getSequence() {
        return sequence;
    }

    public void setSequence(Sequence<T> sequence) {
        this.sequence = sequence;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public abstract T getValue();
}
