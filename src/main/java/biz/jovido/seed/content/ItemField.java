package biz.jovido.seed.content;

import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class ItemField extends Field {

    private StructureFilter structureFilter;

    public List<Structure> getAcceptedStructures() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Payload createPayload() {
        return new ItemPayload();
    }

    public ItemField(String propertyName) {
        super(propertyName);
    }
}
