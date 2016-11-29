package biz.jovido.seed.model.content.node.fragment.payload

import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Lob

/**
 *
 * @author Stephan Grundner
 */
@DiscriminatorValue(Type.DECIMAL)
@Entity
class DecimalPayload extends NumberPayload<BigDecimal> {

    @Lob
    @Column(name = 'decimal_value')
    BigDecimal value
}
