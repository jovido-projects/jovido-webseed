package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"field_id", "ordinal"}))
public abstract class Payload<T> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, targetEntity = Field.class)
    @JoinColumn(name = "field_id")
    private Field<T> field;

    private int ordinal;

    public Field<T> getField() {
        return field;
    }

    /* public */ void setField(Field<T> field) {
        this.field = field;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public abstract T getValue();
    public abstract void setValue(T value);
}
