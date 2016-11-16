package biz.jovido.webseed.model.content

import groovy.transform.CompileStatic

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = '\"constraint\"', uniqueConstraints =
        @UniqueConstraint(columnNames = ['structure_id', 'name']))
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@CompileStatic
abstract class Constraint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
//    @JoinColumns([
//            @JoinColumn(name = 'structure_id', referencedColumnName = 'id'),
//            @JoinColumn(name = 'structure_revision_id', referencedColumnName = 'revision_id')])
    @JoinColumn(name = 'structure_id')
    Structure structure

    void setStructure(Structure structure) {
        assert name != null

        this.structure?.@constraints?.remove(this)

        if (structure != null) {
            structure.@constraints.put(name, this)
        }

        this.structure = structure
    }

    @Column(nullable = false, updatable = false)
    String name

    @Column(name = 'min_values')
    Long minValues

    @Column(name = 'max_values')
    Long maxValues

    Boolean editable = true
    Boolean updatable = true
    Boolean nullable = true
}
