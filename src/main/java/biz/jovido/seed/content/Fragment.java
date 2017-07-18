package biz.jovido.seed.content;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Fragment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Structure structure;

//    @ManyToOne
//    private Item item;

    private String title;

    @OneToOne(cascade = {})
    private FragmentPayload referringPayload;

    @CreatedDate
    private Date created;

    private String path;

    @OneToMany(mappedBy = "fragment",
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER,
            orphanRemoval = true)
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

//    public Item getItem() {
//        return item;
//    }
//
//    public void setItem(Item item) {
//        this.item = item;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FragmentPayload getReferringPayload() {
        return referringPayload;
    }

    public void setReferringPayload(FragmentPayload referringPayload) {
        this.referringPayload = referringPayload;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Field> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public boolean isReferred() {
        return referringPayload != null;
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

    public Object getValue(String fieldName, int index) {
        Field field = getField(fieldName);
        if (index >= field.size()) {
            return null;
        }

        Payload payload = field.getPayloads().get(index);
        return payload.getValue();
    }

    public void setValue(String fieldName, int index, Object value) {
        Field field = getField(fieldName);
        List<Payload> payloads = field.getPayloads();
        if (index >= payloads.size()) {
            int remaining = (index + 1) - payloads.size();
            while (remaining-- > 0) {
                Attribute attribute = field.getAttribute();
                Payload payload = attribute.createPayload();
                field.appendPayload(payload);
            }
        }

        Payload payload = payloads.get(index);
        payload.setValue(value);
    }
}
