package biz.jovido.seed.model.content.node.fragment.payload

import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Lob

/**
 *
 * @author Stephan Grundner
 */
@DiscriminatorValue(Type.INTEGER)
@Entity
class IntegerPayload extends NumberPayload<BigInteger> {

    @Lob
    @Column(name = 'integer_value')
    BigInteger value
}
