package biz.jovido.seed.content.model;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
public class RelationAttribute extends Attribute {

    private int capacity;
    private int required;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "accepted_type_name")
//    private final Set<String> acceptedTypeNames = new HashSet<>();

    @OneToMany
    @JoinTable(name = "accepted_type",
            joinColumns = @JoinColumn(name = "attribute_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    @MapKey(name = "name")
    private final Map<String, Type> acceptedTypes = new LinkedHashMap<>();

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

    public Collection<Type> getAcceptedTypes() {
        return Collections.unmodifiableCollection(acceptedTypes.values());
    }

    public Type addAcceptedType(Type type) {
        return acceptedTypes.put(type.getName(), type);
    }

    public Set<String> getAcceptedTypeNames() {
        return Collections.unmodifiableSet(acceptedTypes.keySet());
    }

    @Override
    public RelationPayload createPayload() {
        RelationPayload payload = new RelationPayload();
        payload.setRelation(new Relation());
        return payload;
    }
}
