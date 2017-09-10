package biz.jovido.seed.content.model;

import biz.jovido.seed.content.service.StructureService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class Configurer implements Configuration {

    private final StructureService structureService;

    private Set<Structure> structures = new HashSet<>();

    @Override
    public HierarchyConfigurer forHierarchy(String hierarchyName) {
        Hierarchy hierarchy = structureService.getOrCreateHierarchy(hierarchyName);
        return new HierarchyConfigurer(this, hierarchy);
    }

    @Override
    public StructureConfigurer forStructure(String typeName, int revision) {
        Type type = structureService.getOrCreateType(typeName);
        Structure structure = structureService.getOrCreateStructure(type, revision);
        structures.add(structure);
        return new StructureConfigurer(this, structure);
    }

    @Override
    public void apply() {
        for (Structure structure : structures) {
            structureService.saveStructure(structure);
            structureService.activateStructure(structure);
        }
    }

    public Configurer(StructureService structureService) {
        this.structureService = structureService;
    }
}
