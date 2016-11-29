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
@DiscriminatorValue(Type.DATE)
@Entity
class DatePayload extends Payload<Date> {

    @Lob
    @Column(name = 'date_value')
    Date value
}
