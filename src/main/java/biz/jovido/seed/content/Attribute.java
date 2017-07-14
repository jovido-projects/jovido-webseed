package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"structure_id", "field_name"}))
public abstract class Attribute {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @Column(name = "field_name", nullable = false)
    private String fieldName;

    private int ordinal;

    private int capacity = 1;
    private int required = 1;

    public Long getId() {
        return id;
    }

    /* public */ void setId(Long id) {
        this.id = id;
    }

    public Structure getStructure() {
        return structure;
    }

    /* public */ void setStructure(Structure structure) {
        this.structure = structure;
    }

    public String getFieldName() {
        return fieldName;
    }

    /* public */ void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public abstract Payload createPayload();

    @Deprecated
    public Attribute(String fieldName) {
        this.fieldName = fieldName;
    }

    public Attribute(Structure structure, String fieldName) {
        this.fieldName = fieldName;

        structure.putAttribute(this);
    }

    public Attribute() {}
}
