package biz.jovido.seed.content.model.node;

import biz.jovido.seed.content.model.node.field.Constraint;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Table(name = "node_field", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "type_id"}))
@Entity
public class Field {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @Column(nullable = false)
    private int ordinal = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "constraint_id", nullable = false)
    private Constraint constraint;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }
}
