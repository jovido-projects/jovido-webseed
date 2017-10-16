package biz.jovido.seed.content;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class Configurer implements Configuration {

    private final HierarchyService hierarchyService;
    private final TypeService typeService;

    private Set<Structure> structures = new HashSet<>();

    @Override
    public HierarchyConfigurer forHierarchy(String hierarchyName) {
        Hierarchy hierarchy = hierarchyService.getOrCreateHierarchy(hierarchyName);
        return new HierarchyConfigurer(this, hierarchy);
    }

    @Override
    public TypeConfigurer forType(String typeName, int revision) {
        Type type = typeService.getOrCreateType(typeName);
        Structure structure = typeService.getOrCreateStructure(type, revision);
        structures.add(structure);
        return new TypeConfigurer(this, structure);
    }

    @Override
    public void apply() {
        for (Structure structure : structures) {
            typeService.saveStructure(structure);
            Type type = structure.getType();
            int revision = structure.getRevision();
            typeService.setActiveRevision(type.getName(), revision);
        }
    }

    public Configurer(HierarchyService hierarchyService, TypeService typeService) {
        this.hierarchyService = hierarchyService;
        this.typeService = typeService;
    }
}
