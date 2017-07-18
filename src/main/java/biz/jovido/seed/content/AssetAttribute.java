package biz.jovido.seed.content;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class AssetAttribute extends Attribute {

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
}
