package biz.jovido.seed.model.content.node.fragment.payload

import biz.jovido.seed.model.content.node.fragment.Payload

import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Lob

/**
 *
 * @author Stephan Grundner
 */
@DiscriminatorValue(Type.TEXT)
@Entity
class TextPayload extends Payload<String> {

    @Lob
    @Column(name = 'text_value')
    String value
}
