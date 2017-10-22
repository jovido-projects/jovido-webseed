package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface Configuration {

    HierarchyConfigurer forHierarchy(String hierarchyName);
    StructureConfigurer forStructure(String typeName, int revision);

    void apply();
}
