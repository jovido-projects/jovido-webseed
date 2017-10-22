package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface StructureConfiguration extends Configuration {

    StructureConfigurer setPublishable(boolean publishable);

    TextAttributeConfigurer addTextAttribute(String name);
    ImageAttributeConfigurer addImageAttribute(String name);
    ItemAttributeConfigurer addItemAttribute(String name);
    LinkAttributeConfigurer addLinkAttribute(String name);
}
