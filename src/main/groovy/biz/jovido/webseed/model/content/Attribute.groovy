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
class Attribute implements List<Payload<?>> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
    Fragment fragment

//    void setFragment(Fragment fragment) {
//        this.fragment?.@attributes?.remove(field, this)
//
//        if (fragment != null) {
//            fragment.@attributes.put(field, this)
//        }
//
//        this.fragment = fragment
//    }

    @ManyToOne(targetEntity = Field, fetch = FetchType.EAGER)
    @JoinColumn(name = 'field_id')
    Field field

    @OneToMany(mappedBy = 'attribute', cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderColumn(name = 'ordinal')
    @Delegate
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

    Object getValue(int index) {
        getPayload(index)?.value
    }

    void setValue(int index, Object value) {
        def payload = getPayload(index) as Payload<?>
        payload?.setValue(value)
    }

    Object getValue() {
        getValue(0)
    }

    void setValue(Object value) {
        setValue(0, value)
    }

    Payload<?> removePayload(int index) {
        def payload = payloads.remove(index)
        payload?.attribute = null

        payload
    }

    Object removeValue(int index) {
        def payload = removePayload(index)

        payload?.value
    }
}
