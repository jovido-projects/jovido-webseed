package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface StructureConfiguration extends Configuration {

    StructureConfigurer setPublishable(boolean publishable);
    StructureConfigurer setLabelAttribute(String name);

    TextAttributeConfigurer addTextAttribute(String name);
    BooleanAttributeConfigurer addBooleanAttribute(String name);
    IconAttributeConfigurer addIconAttribute(String name);
    ImageAttributeConfigurer addImageAttribute(String name);
    ItemAttributeConfigurer addItemAttribute(String name);
    LinkAttributeConfigurer addLinkAttribute(String name);
}
