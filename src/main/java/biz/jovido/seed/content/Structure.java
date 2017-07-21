package biz.jovido.seed.content;

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

    public List<Field> getFields() {
        return fields.values().stream().collect(Collectors.toList());
    }

    public Structure(String name) {
        this.name = name;
    }
}
