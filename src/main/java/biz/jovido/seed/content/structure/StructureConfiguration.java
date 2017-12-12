package biz.jovido.seed.content.structure;

/**
 * @author Stephan Grundner
 */
public interface StructureConfiguration {

    TextAttributeConfigurer configureTextAttribute(String attributeName);
    DateAttributeConfigurer configureDateAttribute(String attributeName);
    FragmentAttributeConfigurer configureFragmentAttribute(String attributeName);

    Structure getStructure();
}
