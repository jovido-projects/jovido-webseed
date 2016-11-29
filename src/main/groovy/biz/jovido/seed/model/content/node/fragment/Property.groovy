package biz.jovido.seed.model.content.node.fragment

import biz.jovido.seed.model.content.node.Field
import biz.jovido.seed.model.content.node.Fragment

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'node_fragment_property', uniqueConstraints =
        @UniqueConstraint(columnNames = ['field_id', 'fragment_id']))
@Entity
class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
    @JoinColumn(name = 'field_id')
    Field field

    @ManyToOne
    @JoinColumn(name = 'fragment_id')
    Fragment fragment

    @OneToMany(mappedBy = 'property')
    @OrderColumn(name = 'ordinal')
    protected final List<Payload> payloads = new ArrayList<>()

    List<Payload> getPayloads() {
        Collections.unmodifiableList(payloads)
    }

    Payload getPayload(int index) {
        payloads.get(index)
    }

    void setPayload(int index, Payload payload) {
        payloads.set(index, payload)
    }
}
