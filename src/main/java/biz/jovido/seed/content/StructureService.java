package biz.jovido.seed.content;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

//    @Autowired
//    private StructureRepository structureRepository;

//    private Map<String, Integer> revisionByStructureName = new HashMap<>();

    private Map<String, Structure> structureByName = new HashMap<>();

    public Structure saveStructure(Structure structure) {
        structureByName.put(structure.getName(), structure);
        return structure;
    }

    public Structure getStructure(String name) {
        return structureByName.get(name);
    }

    public Structure getOrCreateStructure(String name) {
        Structure structure = getStructure(name);
        if (structure == null) {
            structure = new Structure();
            structure.setName(name);
            structure = saveStructure(structure);
        }

        return structure;
    }

    public Collection<Structure> getAllStructures() {
        return Collections.unmodifiableCollection(structureByName.values());
    }

    public List<Structure> findPublishableStructures() {
        return getAllStructures().stream()
                .filter(Structure::isPublishable)
                .collect(Collectors.toList());
    }
}
