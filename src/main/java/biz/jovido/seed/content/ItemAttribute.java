package biz.jovido.seed.content;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class ItemAttribute extends Attribute {

    private final Set<String> acceptedStructureNames = new HashSet<>();

    public Set<String> getAcceptedStructureNames() {
        return acceptedStructureNames;
    }

    @Override
    public Payload createPayload() {
        return new ItemRelation();
    }
}
