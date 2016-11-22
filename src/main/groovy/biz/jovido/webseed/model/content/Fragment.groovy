package biz.jovido.webseed.model.content

import groovy.transform.CompileStatic
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'fragment')
@EntityListeners(AuditingEntityListener)
@IdClass(FragmentKey)
@Entity
@CompileStatic
class Fragment {

    static class FragmentKey implements Serializable {
        String id
        String revisionId


        @Override
        int hashCode() {
            new HashCodeBuilder()
                    .append(id)
                    .append(revisionId)
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

            def other = (FragmentKey) obj
            new EqualsBuilder()
                    .append(id, other.id)
                    .append(revisionId, other.revisionId)
                    .equals
        }
    }

    @Id
    String id

    @Id
    @Column(name = 'revision_id', unique = true)
    String revisionId

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = 'history_id', nullable = false, updatable = false)
    FragmentHistory history

    void setHistory(FragmentHistory history) {
        this.history?.@revisions?.remove(this)

        if (history != null) {
            history.@revisions.add(this)
        }

        this.history = history
    }

    @ManyToOne
    @JoinColumn(name = 'fragment_type_id')
    FragmentType type

    @OneToMany(mappedBy = 'fragment', cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = 'field_id')
    final Map<Field, Attribute> attributes = new HashMap<>()

    Attribute getAttribute(Field field) {
        attributes.get(field)
    }

    void putAttribute(Attribute attribute) {
        attributes.put(attribute.field, attribute)
        attribute.@fragment = this
    }

    void removeAttribute(Attribute attribute) {
        if (attributes.remove(attribute.field, attribute)) {
            attribute.@fragment = null
        }
    }

    @CreatedDate
    @Column(name = 'created')
    Date created

    @LastModifiedDate
    @Column(name = 'last_modified')
    Date lastModifie
}
