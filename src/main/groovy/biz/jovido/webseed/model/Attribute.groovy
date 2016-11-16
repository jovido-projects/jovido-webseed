package biz.jovido.webseed.model

import biz.jovido.webseed.model.value.Payload
import groovy.transform.CompileStatic

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'attribute')
@Entity
@CompileStatic
class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
    Fragment fragment

    @ManyToOne(targetEntity = Field, fetch = FetchType.EAGER)
    @JoinColumn(name = 'field_id')
    Field field

    @OneToMany(mappedBy = 'attribute', cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderColumn(name = 'ordinal')
    final List<Payload<?>> values = new ArrayList<>()

    Payload<?> getPayload(int index) {
        if (index >= values.size()) {
            return null
        }

        values.get(index)
    }

    Payload<?> setPayload(int index, Payload<?> payload) {
        payload?.@attribute = this

        def required = index - values.size() + 1
        if (required > 0) {
            for (int i = 0; i < required; i++) {
                values.add(null)
            }
            assert values.size() == index + 1
        }

        values.set(index, payload)
    }

    Object getValue(int index) {
        getPayload(index)?.value
    }
}
