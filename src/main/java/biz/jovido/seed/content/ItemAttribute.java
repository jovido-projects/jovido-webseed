package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Entity
public class ItemAttribute extends Attribute {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "accepted_type",
            uniqueConstraints = @UniqueConstraint(columnNames = {"attribute_id", "name"}),
            joinColumns = @JoinColumn(name = "attribute_id"))
    @Column(name = "name")
    private final Set<String> acceptedTypeNames = new HashSet<>();

    public Set<String> getAcceptedTypeNames() {
        return acceptedTypeNames;
    }

    @Override
    public Payload createPayload() {
        return new ItemPayload();
    }
}
