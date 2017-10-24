package biz.jovido.seed.content;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class Configurer implements Configuration {

    private final HierarchyService hierarchyService;
    private final StructureService structureService;

    private Set<Structure> structures = new HashSet<>();

    @Override
    public HierarchyConfigurer createHierarchy(String hierarchyName) {
        Hierarchy hierarchy = hierarchyService.getOrCreateHierarchy(hierarchyName);
        return new HierarchyConfigurer(this, hierarchy);
    }

    @Override
    public StructureConfigurer createStructure(String structureName) {
        Structure structure = structureService.getOrCreateStructure(structureName);
        structures.add(structure);
        return new StructureConfigurer(this, structure);
    }

    @Override
    public void apply() {
        for (Structure structure : structures) {
            structureService.saveStructure(structure);
        }
    }

    public Configurer(HierarchyService hierarchyService, StructureService structureService) {
        this.hierarchyService = hierarchyService;
        this.structureService = structureService;
    }
}
