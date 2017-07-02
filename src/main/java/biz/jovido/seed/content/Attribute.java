package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Attribute {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Structure structure;

    private String name;

    private int capacity;
    private int required;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Structure getStructure() {
        return structure;
    }

    /* public */ void setStructure(Structure structure) {
        this.structure = structure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Attribute(String name) {
        this.name = name;
    }

    public Attribute() {}
}
