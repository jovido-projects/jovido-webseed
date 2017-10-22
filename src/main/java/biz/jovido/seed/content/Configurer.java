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
    public HierarchyConfigurer forHierarchy(String hierarchyName) {
        Hierarchy hierarchy = hierarchyService.getOrCreateHierarchy(hierarchyName);
        return new HierarchyConfigurer(this, hierarchy);
    }

    @Override
    public StructureConfigurer forStructure(String structureName, int structureRevision) {
        Structure structure = structureService.getOrCreateStructure(structureName, structureRevision);
        structures.add(structure);
        return new StructureConfigurer(this, structure);
    }

    @Override
    public void apply() {
        for (Structure structure : structures) {
            structureService.saveStructure(structure);
            structureService.setActiveStructureRevision(
                    structure.getName(),
                    structure.getRevision());
        }
    }

    public Configurer(HierarchyService hierarchyService, StructureService structureService) {
        this.hierarchyService = hierarchyService;
        this.structureService = structureService;
    }
}
