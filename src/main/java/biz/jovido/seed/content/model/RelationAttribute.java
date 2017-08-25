package biz.jovido.seed.content.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class RelationAttribute extends Attribute {

    private int capacity;
    private int required;

    private final Set<String> accepted = new HashSet<>();

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public Set<String> getAcceptedStructureNames() {
        return accepted;
    }

    @Override
    public RelationPayload createPayload() {
        return new RelationPayload();
    }
}
