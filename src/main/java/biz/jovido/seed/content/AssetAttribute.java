package biz.jovido.seed.content;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Entity
public class AssetAttribute extends Attribute {

    @ElementCollection
    @CollectionTable(name = "file_name_extension")
    private final Set<String> fileNameExtensions = new HashSet<>();

    private long contentLengthLimit = Long.MAX_VALUE;

    public Set<String> getFileNameExtensions() {
        return fileNameExtensions;
    }

    public long getContentLengthLimit() {
        return contentLengthLimit;
    }

    public void setContentLengthLimit(long contentLengthLimit) {
        this.contentLengthLimit = contentLengthLimit;
    }

    @Override
    public AssetPayload createPayload() {
        return new AssetPayload();
    }

    public AssetAttribute(Structure structure, String fieldName) {
        super(structure, fieldName);
    }

    public AssetAttribute() {}
}
