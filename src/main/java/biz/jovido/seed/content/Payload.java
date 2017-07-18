package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payload {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Field field;

    @Transient
    private boolean toBeRemoved;

    private int ordinal;

    public Field getField() {
        return field;
    }

    /* public */ void setField(Field field) {
        this.field = field;
    }

    public boolean isToBeRemoved() {
        return toBeRemoved;
    }

    public void setToBeRemoved(boolean toBeRemoved) {
        this.toBeRemoved = toBeRemoved;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public abstract Object getValue();
    public abstract void setValue(Object value);
}
