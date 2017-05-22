package biz.jovido.seed.content;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    private final Map<String, Structure> structures = new HashMap<>();

    public Structure getStructure(String name) {
        return structures.get(name);
    }

    public Structure registerStructure(Structure structure) {
        return structures.put(structure.getName(), structure);
    }
}
