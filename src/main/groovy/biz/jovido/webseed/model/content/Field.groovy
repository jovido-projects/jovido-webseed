package biz.jovido.webseed.model.content

import groovy.transform.CompileStatic
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.springframework.util.StringUtils

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
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

    void setName(String name) {
        this.name = name

        if (fragmentType != null) {
            fragmentType.fields.put(name, this)
        }

        if (group != null) {
            group.fields.put(name, this)
        }
    }

    @ManyToOne
    @JoinColumn(name = 'fragment_type_id', updatable = false)
    protected FragmentType fragmentType

    FragmentType getFragmentType() {
        return fragmentType
    }

    @ManyToOne
    @JoinColumn(name = 'constraint_id', updatable = false)
    Constraint constraint

    @ManyToOne
    @JoinColumn(name = 'field_group_id', updatable = false)
    FieldGroup group

    void setGroup(FieldGroup group) {
        this.group?.@fields?.remove(name)

        if (group != null && !StringUtils.isEmpty(name)) {
            group.@fields.put(name, this)
        }

        this.group = group
    }

    private int ordinal

    int getOrdinal() {
        ordinal
    }

    protected void setOrdinal(int ordinal) {
        this.ordinal = ordinal
    }

    @Override
    int hashCode() {
        new HashCodeBuilder()
                .append(name)
                .append(fragmentType?.name)
                .append(constraint?.name)
                .append(ordinal)
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
                .append(constraint?.name, other.constraint?.name)
                .append(ordinal, other.ordinal)
                .equals
    }
}
