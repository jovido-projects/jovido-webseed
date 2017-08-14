package biz.jovido.seed.content.model;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"property_id", "ordinal"}))
public abstract class Value {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    Property property;

    private int ordinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public abstract Object getData();
    public abstract void setData(Object data);
}
