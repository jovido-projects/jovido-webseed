package biz.jovido.seed.model.content.node

import biz.jovido.seed.model.content.node.field.Constraint

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'node_field',uniqueConstraints =
        @UniqueConstraint(columnNames = ['name', 'type_id']))
@Entity
class Field {

    @Id
    @GeneratedValue
    Long id

    @Column(nullable = false)
    String name

    @ManyToOne
    @JoinColumn(name = 'type_id', nullable = false)
    Type type

    @Column(nullable = false)
    int ordinal = 0

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = 'constraint_id', nullable = false)
    Constraint constraint
}
