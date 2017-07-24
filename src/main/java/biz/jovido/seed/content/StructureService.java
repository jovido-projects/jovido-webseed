package biz.jovido.seed.content;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    private final Map<String, Structure> structures = new ConcurrentHashMap<>();

    public Structure getStructure(String name) {
        return structures.get(name);
    }

    public StructureConfigurer configure(String name) {
        Structure structure = getStructure(name);
        if (structure == null) {
            structure = new Structure(name);
            structures.put(name, structure);
        }

        return new StructureBuilder(structure);
    }
}
