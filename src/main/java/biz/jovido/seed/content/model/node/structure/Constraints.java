package biz.jovido.seed.content.model.node.structure;

import biz.jovido.seed.content.model.node.fragment.Property;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Table(name = "constraints")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Entity
public abstract class Constraints {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "min_values")
    private int minimumNumberOfValues;

    @Column(name = "max_values")
    private int maximumNumberOfValues;

    @Transient
    private final Class<? extends Property> propertyClazz;

    private boolean nullable;

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

    public int getMinimumNumberOfValues() {
        return minimumNumberOfValues;
    }

    public void setMinimumNumberOfValues(int minimumNumberOfValues) {
        this.minimumNumberOfValues = minimumNumberOfValues;
    }

    public int getMaximumNumberOfValues() {
        return maximumNumberOfValues;
    }

    public void setMaximumNumberOfValues(int maximumNumberOfValues) {
        this.maximumNumberOfValues = maximumNumberOfValues;
    }

    public boolean getNullable() {
        return nullable;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public Class<? extends Property> getPropertyClazz() {
        return propertyClazz;
    }

    protected Constraints(Class<? extends Property> propertyClazz) {
        this.propertyClazz = propertyClazz;
    }
}
