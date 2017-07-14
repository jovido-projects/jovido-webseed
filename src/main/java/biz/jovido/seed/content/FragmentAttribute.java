package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Entity
public class FragmentAttribute extends Attribute {

    private boolean embeddable;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "structure_name",
            uniqueConstraints = @UniqueConstraint(columnNames = {"attribute_id", "name"}),
            joinColumns = @JoinColumn(name = "attribute_id"))
    @Column(name = "name")
    private final Set<String> structureNames = new HashSet<>();

    public boolean isEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

    public Set<String> getStructureNames() {
        return structureNames;
    }

    @Override
    public Payload createPayload() {
        return new FragmentPayload();
    }

    public FragmentAttribute(Structure structure, String fieldName) {
        super(structure, fieldName);
    }

    public FragmentAttribute() {}
}
