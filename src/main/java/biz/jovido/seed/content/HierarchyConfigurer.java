package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class HierarchyConfigurer implements Configuration {

    private final Configurer parentConfigurer;
    private final Hierarchy hierarchy;

    @Override
    public HierarchyConfigurer createHierarchy(String hierarchyName) {
        return parentConfigurer.createHierarchy(hierarchyName);
    }

    @Override
    public StructureConfigurer createStructure(String typeName) {
        return parentConfigurer.createStructure(typeName);
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
