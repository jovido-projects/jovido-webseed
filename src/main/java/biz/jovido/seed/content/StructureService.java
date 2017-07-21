package biz.jovido.seed.content;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    private Map<String, Structure> structures = new HashMap<>();

    public StructureConfigurer configure(String name) {
        Structure structure = structures.get(name);
        if (structure == null) {
            structure = new Structure(name);
            structures.put(name, structure);
        }

        return new StructureBuilder(structure);
    }

    public Structure getStructure(String name) {
        return structures.get(name);
    }
}
