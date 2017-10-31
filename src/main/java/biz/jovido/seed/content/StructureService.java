package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

    @Autowired
    private MessageSource messageSource;

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

    public String getDisplayName(Structure structure, Locale locale) {
        if (structure != null) {
            String code = String.format("seed.structure.%s", structure.getName());
            return messageSource.getMessage(code, new Object[] {}, structure.getName(), locale);
        }

        return null;
    }
}
