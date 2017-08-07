package biz.jovido.seed.content;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    private Map<String, Structure> structures = new ConcurrentHashMap<>();

    public Structure getStructure(String name) {
        return structures.get(name);
    }

    private void putStructure(Structure structure) {
        structures.put(structure.getName(), structure);
    }

    public StructureConfigurer configure(String name) {
        Structure structure = getStructure(name);
        if (structure == null) {
            structure = new Structure();
            structure.setName(name);
            putStructure(structure);
        }

        return new StructureConfiguration(structure, this);
    }
}
