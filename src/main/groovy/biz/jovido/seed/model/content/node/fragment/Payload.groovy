package biz.jovido.seed.model.content.node.fragment

import biz.jovido.seed.model.Auditee

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'node_fragment_payload', uniqueConstraints =
        @UniqueConstraint(columnNames = ['property_id', 'ordinal']))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = 'type')
@Entity
abstract class Payload<T> implements Auditee {

    static final class Type {

        public static final String ASSET = 'asset'
        public static final String DATE = 'date'
        public static final String DECIMAL = 'decimal'
        public static final String INTEGER = 'integer'
        public static final String TEXT = 'text'
        public static final String FRAGMENT = 'fragment'
        public static final String NODE = 'node'

        private Type() {}
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
    @JoinColumn(name = 'property_id', nullable = false)
    Property property

    int ordinal

    @Transient
    abstract T getValue()
    abstract void setValue(T value)
}
