package biz.jovido.seed.content.model.node.fragment;

import groovy.transform.CompileStatic;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Table(name = "node_fragment_payload", uniqueConstraints = @UniqueConstraint(columnNames = {"property_id", "ordinal"}))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Entity
@CompileStatic
public abstract class Payload<T> {

    public final static class Type {

        public static final String ASSET = "asset";
        public static final String DATE = "date";
        public static final String DECIMAL = "decimal";
        public static final String INTEGER = "integer";
        public static final String TEXT = "text";
        public static final String FRAGMENT = "fragment";
        public static final String NODE = "node";

        private Type() {
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    private int ordinal;

    public abstract T getValue();

    public abstract void setValue(T value);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
