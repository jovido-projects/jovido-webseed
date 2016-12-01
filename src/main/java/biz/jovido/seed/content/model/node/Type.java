package biz.jovido.seed.content.model.node;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Table(name = "node_type")
@Entity
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @MapKeyColumn(name = "name")
//    @OrderColumn(name = "ordinal")
    @OrderBy("ordinal ASC")
    protected final Map<String, Field> fieldMapping = new LinkedHashMap<>();

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
        field.setType(this);
        field.setOrdinal(fieldMapping.size());
        Field previous = fieldMapping.put(name, field);
        assert previous == null;

        return field;
    }

    public Field removeField(String fieldName) {
        Field field = fieldMapping.remove(fieldName);

        return field;
    }
}
