package biz.jovido.seed.content.model;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Entity
public class RelationAttribute extends Attribute {

    private int capacity;
    private int required;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "accepted_type")
    private final Set<String> acceptedTypeNames = new HashSet<>();

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
        return acceptedTypeNames;
    }

    @Override
    public RelationPayload createPayload() {
        RelationPayload payload = new RelationPayload();
        payload.setRelation(new Relation());
        return payload;
    }
}
