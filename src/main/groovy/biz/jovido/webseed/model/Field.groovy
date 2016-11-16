package biz.jovido.webseed.model

import groovy.transform.CompileStatic
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'field', uniqueConstraints =
        @UniqueConstraint(columnNames = ['name', 'fragment_type_id']))
@Entity
@CompileStatic
class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(nullable = false, updatable = false)
    String name

    @ManyToOne
    @JoinColumn(name = 'fragment_type_id', updatable = false)
    protected FragmentType fragmentType

    FragmentType getFragmentType() {
        return fragmentType
    }

    @ManyToOne
    @JoinColumn(name = 'constraint_id', updatable = false)
    Constraint constraint

    int ordinal

    @Override
    int hashCode() {
        new HashCodeBuilder()
                .append(name)
                .append(fragmentType?.name)
                .append(ordinal)
                .append(constraint?.name)
                .build()
    }

    @Override
    boolean equals(Object obj) {
        if (obj == null) {
            return false
        }

        if (getClass() != obj.getClass()) {
            return false
        }

        def other = (Field) obj
        new EqualsBuilder()
                .append(name, other.name)
                .append(fragmentType?.name, other.fragmentType?.name)
                .append(ordinal, other.ordinal)
                .append(constraint?.name, other.constraint?.name)
                .equals
    }
}
