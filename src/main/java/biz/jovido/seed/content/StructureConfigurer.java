package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface StructureConfigurer {

//    StructureConfigurer setName(String name);
    StructureConfigurer setRevision(int revision);

    TextAttributeConfigurer addTextAttribute(String name);
    ItemAttributeConfigurer addItemAttribute(String name);

    Structure build(StructureService structureService);
}
