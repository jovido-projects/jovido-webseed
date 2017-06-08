package biz.jovido.seed.content;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Service
public class TypeService {

    private final Map<String, Type> types = new HashMap<>();

    public Type getType(String name) {
        return types.get(name);
    }

    public Type registerType(Type type) {
        return types.put(type.getName(), type);
    }
}
