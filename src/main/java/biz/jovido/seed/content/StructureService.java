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
public class StructureService {

    @Autowired
    private StructureRepository structureRepository;

    private Map<String, Integer> revisionByStructureName = new HashMap<>();

    public int getActiveStructureRevision(String structureName) {
        return revisionByStructureName.get(structureName);
    }

    public void setActiveStructureRevision(String structureName, int revision) {
        revisionByStructureName.put(structureName, revision);
    }

    public Structure saveStructure(Structure structure) {
        return structureRepository.saveAndFlush(structure);
    }

    public Structure getStructure(String name, int revision) {
        return structureRepository.findByNameAndRevision(name, revision);
    }

    public Structure getStructure(String name) {
        int revision = revisionByStructureName.get(name);
        return getStructure(name, revision);
    }

    public Structure getOrCreateStructure(String name, int revision) {
        Structure structure = getStructure(name, revision);
        if (structure == null) {
            structure = new Structure();
            structure.setName(name);
            structure.setRevision(revision);
            structure = saveStructure(structure);
        }

        return structure;
    }

    public List<Structure> findPublishableStructures() {
        List<Structure> structures = structureRepository.findAllByPublishableIsTrue();

        return structures;
    }
}
