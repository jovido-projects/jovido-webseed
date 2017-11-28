package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface StructureConfiguration extends Configuration {

    StructureConfigurer setNestedOnly(boolean nestedOnly);
    StructureConfigurer setPublishable(boolean publishable);
    StructureConfigurer setLabelAttribute(String name);
    StructureConfigurer setTemplate(String template);

    TextAttributeConfigurer addTextAttribute(String name);
    YesNoAttributeConfigurer addYesNoAttribute(String name);
    IconAttributeConfigurer addIconAttribute(String name);
    ImageAttributeConfigurer addImageAttribute(String name);
    ItemAttributeConfigurer addItemAttribute(String name);
    LinkAttributeConfigurer addLinkAttribute(String name);
    SelectionAttributeConfigurer addSelectionAttribute(String name);
    DateAttributeConfigurer addDateAttribute(String name);
}
