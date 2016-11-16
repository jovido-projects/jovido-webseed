package biz.jovido.webseed.model

import groovy.transform.CompileStatic

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = '\"constraint\"', uniqueConstraints =
        @UniqueConstraint(columnNames = ['structure_id', 'structure_revision_id', 'name']))
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@CompileStatic
abstract class Constraint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String name

    @ManyToOne
    @JoinColumns([
            @JoinColumn(name = 'structure_id', referencedColumnName = 'id'),
            @JoinColumn(name = 'structure_revision_id', referencedColumnName = 'revision_id')])
    Structure structure

    @Column(name = 'min_values')
    Long minValues

    @Column(name = 'max_values')
    Long maxValues

    Boolean editable = true
    Boolean updatable = true
    Boolean nullable = true
}
