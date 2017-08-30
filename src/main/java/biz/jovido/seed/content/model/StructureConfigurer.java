package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public interface StructureConfigurer {

    TextAttributeConfigurer addTextAttribute(String name);
    RelationAttributeConfigurer addRelationAttribute(String name);
    StructureConfigurer setStandalone(boolean standalone);

    Structure build();
    Structure getStructure();
}
