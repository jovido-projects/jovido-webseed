package biz.jovido.webseed.model

import groovy.transform.CompileStatic
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
        Long id
        String revisionId
    }

    @Id
    Long id

    @Column(name = 'revision_id', unique = true)
    String revisionId

    @ManyToOne
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

    Attribute getAttribute(String fieldName) {
        attributes.values().find { it.field?.name == fieldName }
    }

    void putAttribute(Attribute attribute) {
        attributes.put(attribute.field, attribute)
    }

    @CreatedDate
    @Column(name = 'created')
    Date created

    @LastModifiedDate
    @Column(name = 'last_modified')
    Date lastModifie
}
