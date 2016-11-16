package biz.jovido.webseed.model.content

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
    final List<Payload<?>> payloads = new ArrayList<>()

    Payload<?> getPayload(int index) {
        if (index >= payloads.size()) {
            return null
        }

        payloads.get(index)
    }

    Payload<?> setPayload(int index, Payload<?> payload) {
        payload?.@attribute = this

        def required = index - payloads.size() + 1
        if (required > 0) {
            for (int i = 0; i < required; i++) {
                payloads.add(null)
            }
            assert payloads.size() == index + 1
        }

        payloads.set(index, payload)
    }

//    Object getValue(int index) {
//        getPayload(index)?.value
//    }
}
