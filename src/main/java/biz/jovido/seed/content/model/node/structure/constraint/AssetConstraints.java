package biz.jovido.seed.content.model.node.structure.constraint;

import biz.jovido.seed.content.model.node.fragment.property.AssetProperty;
import biz.jovido.seed.content.model.node.structure.Constraints;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
//@Table(name = "asset_constraint")
@DiscriminatorValue("asset")
@Entity
public class AssetConstraints extends Constraints {

    @Column(name = "min_content_length")
    private long minimumContentLength = 0L;

    @Column(name = "max_content_length")
    private long maximumContentLength = Long.MAX_VALUE;

    @ElementCollection
    @CollectionTable(name = "allowed_mime_type",
            joinColumns = @JoinColumn(name = "constraints_id", referencedColumnName = "id"))
    @Column(name = "mime_type")
    private final Set<String> allowedMimeTypes = new LinkedHashSet<>();

    public long getMinimumContentLength() {
        return minimumContentLength;
    }

    public void setMinimumContentLength(long minimumContentLength) {
        this.minimumContentLength = minimumContentLength;
    }

    public long getMaximumContentLength() {
        return maximumContentLength;
    }

    public void setMaximumContentLength(long maximumContentLength) {
        this.maximumContentLength = maximumContentLength;
    }

    public Set<String> getAllowedMimeTypes() {
        return allowedMimeTypes;
    }

    @Deprecated
    public AssetConstraints() {
        super(AssetProperty.class);
    }
}
