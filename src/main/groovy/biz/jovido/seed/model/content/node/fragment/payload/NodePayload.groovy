package biz.jovido.seed.model.content.node.fragment.payload

import biz.jovido.seed.model.content.Node
import biz.jovido.seed.model.content.node.fragment.Payload

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 *
 * @author Stephan Grundner
 */
@DiscriminatorValue(Type.NODE)
@Entity
class NodePayload extends Payload<Node> {

    @ManyToOne
    @JoinColumn(name = 'node_id')
    Node value
}
