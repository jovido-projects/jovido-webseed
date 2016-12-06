package biz.jovido.seed.content.model.node.fragment.property;

import biz.jovido.seed.content.model.Asset;
import biz.jovido.seed.content.model.node.fragment.Property;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue("asset")
@Entity
public class AssetProperty extends Property<Asset> {

    @ManyToMany
    @OrderColumn(name = "ordinal")
    @JoinTable(name = "asset_property",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_id"))
    private final List<Asset> values = new ArrayList<>();

    @Override
    public List<Asset> getValues() {
        return values;
    }

    public AssetProperty() {
        super(Asset.class);
    }
}
