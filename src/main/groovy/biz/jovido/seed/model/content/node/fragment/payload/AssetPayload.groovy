package biz.jovido.seed.model.content.node.fragment.payload

import biz.jovido.seed.model.content.Asset
import biz.jovido.seed.model.content.node.fragment.Payload

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 *
 * @author Stephan Grundner
 */
@DiscriminatorValue(Type.ASSET)
@Entity
class AssetPayload extends Payload<Asset> {

    @ManyToOne
    @JoinColumn(name = 'asset_id')
    Asset value
}
