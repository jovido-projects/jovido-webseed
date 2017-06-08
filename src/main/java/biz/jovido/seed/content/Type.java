package biz.jovido.seed.content;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class Type {

    private final String name;

    private String label;

    private final Map<String, Field> fields = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<String> getFieldNames() {
        return Collections.unmodifiableSet(fields.keySet());
    }

    public Collection<Field> getFields() {
        return Collections.unmodifiableCollection(fields.values());
    }

    public Field getField(String name) {
        return fields.get(name);
    }

    public Field putField(Field field) {
        Field replaced = fields.put(field.getName(), field);
        if (replaced != null) {
            replaced.setType(null);
        }

        field.setType(this);

        return replaced;
    }

    public boolean removeField(Field field) {
        if (fields.remove(field.getName(), field)) {
            field.setType(null);
            return true;
        }

        return false;
    }

    public Type(String name) {
        this.name = name;
    }
}
