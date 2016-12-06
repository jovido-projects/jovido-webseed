package biz.jovido.seed.content.model.node;

import biz.jovido.seed.content.model.node.structure.Field;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Table(name = "structure")
@Entity
public class Structure {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "structure", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @MapKeyColumn(name = "name")
    @OrderBy("ordinal ASC")
    private final Map<String, Field> fieldMapping = new LinkedHashMap<>();

    @OneToOne
    @JoinColumn(name = "label_field_id")
    private Field labelField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getFieldNames() {
        return Collections.unmodifiableSet(fieldMapping.keySet());
    }

    public Collection<Field> getFields() {
        return Collections.unmodifiableCollection(fieldMapping.values());
    }

    public Field getField(String fieldName) {
        return fieldMapping.get(fieldName);
    }

    public Field addField(String name) {
        Field field = getField(name);
        assert field == null;

        field = new Field();
        field.setName(name);
        field.setStructure(this);
        field.setOrdinal(fieldMapping.size());
        Field previous = fieldMapping.put(name, field);
        assert previous == null;

        return field;
    }

    public Field removeField(String fieldName) {
        Field field = fieldMapping.remove(fieldName);

        if (field == labelField) {
            labelField = null;
        }

        return field;
    }

    public Field getLabelField() {
        return labelField;
    }

    public void setLabelField(Field labelField) {
        Assert.isTrue(getFields().contains(labelField));
        this.labelField = labelField;
    }
}
