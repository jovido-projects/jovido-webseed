package biz.jovido.seed.content;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService implements StructureRegistry {

    private Map<String, Structure> structureByName = new HashMap<>();

    public Structure getStructure(String name) {
        return structureByName.get(name);
    }

    public Set<String> getAllStructureNames() {
        return structureByName.keySet();
    }

    public StructureConfigurer configureStructure(String name) {
        Structure structure = getStructure(name);
        if (structure == null) {
            structure = new Structure();
            structure.setName(name);
        }

        return new StructureBuilder(this, name);
    }

    @Transactional
    public Structure registerStructure(Structure structure) {
        structureByName.put(structure.getName(), structure);
        return structure;
    }
}
