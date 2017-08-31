package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public interface Configuration {

    HierarchyConfigurer forHierarchy(String hierarchyName);
    StructureConfigurer forStructure(String typeName, int revision);

    void apply();
}
