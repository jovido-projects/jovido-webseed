package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    @Autowired
    private HierarchyRepository hierarchyRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private StructureRepository structureRepository;

    /* Hierarchies */

    public Hierarchy getHierarchy(String name) {
        return hierarchyRepository.findByName(name);
    }

    public Hierarchy saveHierarchy(Hierarchy hierarchy) {
        return hierarchyRepository.saveAndFlush(hierarchy);
    }

    public Hierarchy getOrCreateHierarchy(String name) {
        Hierarchy hierarchy = getHierarchy(name);
        if (hierarchy == null) {
            hierarchy = new Hierarchy();
            hierarchy.setName(name);
            hierarchy = hierarchyRepository.saveAndFlush(hierarchy);
        }

        return hierarchy;
    }

    /* Types */

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


    /* Structures */

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
