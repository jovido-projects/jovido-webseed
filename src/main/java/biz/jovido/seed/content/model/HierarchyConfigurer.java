package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class HierarchyConfigurer implements Configuration {

    private final Configurer parentConfigurer;
    private final Hierarchy hierarchy;

    @Override
    public HierarchyConfigurer forHierarchy(String hierarchyName) {
        return parentConfigurer.forHierarchy(hierarchyName);
    }

    @Override
    public StructureConfigurer forStructure(String typeName, int revision) {
        return parentConfigurer.forStructure(typeName, revision);
    }

    @Override
    public void apply() {
        parentConfigurer.apply();
    }

    public HierarchyConfigurer(Configurer parentConfigurer, Hierarchy hierarchy) {
        this.parentConfigurer = parentConfigurer;
        this.hierarchy = hierarchy;
    }
}
