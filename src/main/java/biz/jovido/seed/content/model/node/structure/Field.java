package biz.jovido.seed.content.model.node.structure;

import biz.jovido.seed.content.model.node.Structure;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Table(name = "field", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "structure_id"}))
@Entity
public class Field {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "structure_id", nullable = false)
    private Structure structure;

    @Column(nullable = false)
    private int ordinal = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "constraints_id", nullable = false)
    private Constraints constraints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public Constraints getConstraints() {
        return constraints;
    }

    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }
}
