package biz.jovido.seed.model.content.node.field

import biz.jovido.seed.model.content.node.fragment.Payload

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'node_field_constraint')
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = 'type')
@Entity
abstract class Constraint {

    @Id
    @GeneratedValue
    Long id

    @Column(unique = true)
    String name

    @Column(name = 'min_payloads')
    int minimumNumberOfPayloads

    @Column(name = 'max_payloads')
    int maximumNumberOfPayloads

    boolean nullable

    @Transient
    final Set<Class<? extends Payload>> supportedPayloadTypes = new HashSet<>()

    Set<Class<? extends Payload>> getSupportedPayloadTypes() {
        Collections.unmodifiableSet(supportedPayloadTypes)
    }

    protected boolean addSupportedPayloadType(Class<? extends Payload> payloadType) {
        supportedPayloadTypes.add(payloadType)
    }

    protected boolean removeSupportedPayloadType(Class<? extends Payload> payloadType) {
        supportedPayloadTypes.remove(payloadType)
    }
}
