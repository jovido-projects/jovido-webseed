package biz.jovido.seed.content;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Fragment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Structure structure;

    @ManyToOne
    private Item item;

    @OneToOne
    private FragmentPayload dependingPayload;

    @OneToMany(mappedBy = "fragment",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @MapKey(name = "name")
    private final Map<String, Field> fields = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public FragmentPayload getDependingPayload() {
        return dependingPayload;
    }

    public void setDependingPayload(FragmentPayload dependingPayload) {
        this.dependingPayload = dependingPayload;
    }

    public Map<String, Field> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public boolean isDependent() {
        return dependingPayload != null;
    }

    public Field getField(String name) {
        return fields.get(name);
    }

    public Field putField(Field field) {
        Assert.notNull(field, "[field] must not be null");
        String name = field.getName();
        Assert.hasText(name, "[name] must not be empty");
        Field replaced = fields.put(name, field);
        if (replaced != null) {
            replaced.setFragment(null);
        }

        field.setFragment(this);

        return replaced;
    }
}
