package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.Structure;
import biz.jovido.seed.content.model.StructureRepository;
import biz.jovido.seed.content.model.Type;
import biz.jovido.seed.content.model.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private StructureRepository structureRepository;

    public Type getType(String name) {
        return typeRepository.findByName(name);
    }

    public Type getOrCreateType(String name) {
        Type type = typeRepository.findByName(name);
        if (type == null) {
            type = new Type();
            type.setName(name);
            type = typeRepository.saveAndFlush(type);
        }

        return type;
    }

    private Type saveType(Type type) {
        return typeRepository.saveAndFlush(type);
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

    public Structure saveStructure(Structure structure) {
        return structureRepository.saveAndFlush(structure);
    }

    public void activateStructure(Structure structure) {
        Type type = structure.getType();
        type.setActive(structure);
        Type saved = saveType(type);
        assert saved != null;
    }

    public void activateStructure(String typeName, int revision) {
        Type type = getType(typeName);
        Structure structure = type.getStructure(revision);
        activateStructure(structure);
    }

    public List<Structure> findStandaloneStructures() {
        List<Structure> structures = structureRepository.findAllStandalone();

        return structures;
    }
}
