package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "kind")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"property_id", "ordinal"}))
public abstract class Payload<T> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade = {}, fetch = FetchType.EAGER)
    private Property property;

    private int ordinal;

    public Long getId() {
        return id;
    }

    public Property getProperty() {
        return property;
    }

    /* public */ void setProperty(Property property) {
        this.property = property;
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
