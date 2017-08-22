package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public interface StructureConfigurer {

    TextAttributeConfigurer addTextAttribute(String name);
    RelationAttributeConfigurer addRelationAttribute(String name);

    Structure getStructure();
}