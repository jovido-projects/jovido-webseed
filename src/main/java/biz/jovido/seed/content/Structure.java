package biz.jovido.seed.content;

import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class Structure {

    private final String name;

    private final Map<String, Field> fields = new HashMap<>();

    public String getName() {
        return name;
    }

    public List<String> getPropertyNames() {
        return fields.values().stream()
                .sorted(Comparator.comparingInt(Field::getOrdinal))
                .map(Field::getPropertyName)
                .collect(Collectors.toList());
    }

    public Field getField(String propertyName) {
        return fields.get(propertyName);
    }

    public void putField(Field field) {
        Assert.notNull(field, "[field] must not be null");
        Field replaced = fields.put(field.getPropertyName(), field);
        if (field != null) {
            field.setStructure(null);
        }

        field.setStructure(this);
    }

    public Structure(String name) {
        this.name = name;
    }
}
