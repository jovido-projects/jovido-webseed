package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.Structure;
import biz.jovido.seed.content.model.StructureConfiguration;
import biz.jovido.seed.content.model.StructureConfigurer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    public Collection<Structure> getAllStructures() {
        return Collections.unmodifiableCollection(structures.values());
    }

    public List<Structure> findStandaloneStructures() {
        return getAllStructures().stream()
                .filter(Structure::isStandalone)
                .collect(Collectors.toList());
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
