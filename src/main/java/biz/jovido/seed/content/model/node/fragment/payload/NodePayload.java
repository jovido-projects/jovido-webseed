package biz.jovido.seed.content.model.node.fragment.payload;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.fragment.Payload;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue(Payload.Type.NODE)
@Entity
public class NodePayload extends Payload<Node> {

    @ManyToOne
    @JoinColumn(name = "node_id")
    private Node value;

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
    }
}
