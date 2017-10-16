package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private StructureRepository structureRepository;

    private Map<String, Integer> revisionByTypeName = new HashMap<>();

    public Type getType(String name) {
        return typeRepository.findByName(name);
    }

    private Type saveType(Type type) {
        return typeRepository.save(type);
    }

    public Type getOrCreateType(String name) {
        Type type = getType(name);
        if (type == null) {
            type = new Type();
            type.setName(name);
            type = typeRepository.saveAndFlush(type);
        }

        return type;
    }

    public Structure saveStructure(Structure structure) {
        return structureRepository.saveAndFlush(structure);
    }

    public Structure getOrCreateStructure(Type type, int revision) {
        Structure structure = type.getStructure(revision);
        if (structure == null) {
            structure = new Structure();
            type.setStructure(revision, structure);
            structure = saveStructure(structure);
        }

        return structure;
    }

    public List<Structure> findPublishableStructures() {
        List<Structure> structures = structureRepository.findAllByPublishableIsTrue();

        return structures;
    }

    public int getActiveRevision(String typeName) {
        return revisionByTypeName.get(typeName);
    }

    public void setActiveRevision(String typeName, int revision) {
        revisionByTypeName.put(typeName, revision);
    }
}
