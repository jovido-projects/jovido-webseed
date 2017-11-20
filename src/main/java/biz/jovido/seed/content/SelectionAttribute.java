package biz.jovido.seed.content;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class SelectionAttribute extends Attribute {

    private boolean multiselect;
    private final Set<String> options = new LinkedHashSet<>();

    public boolean isMultiselect() {
        return multiselect;
    }

    public void setMultiselect(boolean multiselect) {
        this.multiselect = multiselect;
    }

    public Set<String> getOptions() {
        return options;
    }

    @Override
    public Payload createPayload() {
        return new SelectionPayload();
    }
}
