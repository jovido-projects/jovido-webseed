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

    private String structureName;

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

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

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
}
