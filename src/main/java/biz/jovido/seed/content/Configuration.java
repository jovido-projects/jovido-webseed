package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface Configuration {

    HierarchyConfigurer createHierarchy(String hierarchyName);
    StructureConfigurer createStructure(String typeName, int revision);

    void apply();
}
