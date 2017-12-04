package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface FragmentStructureConfiguration {

    TextPayloadAttributeConfigurer configureTextAttribute(String attributeName);
    DatePayloadAttributeConfigurer configureDateAttribute(String attributeName);
    FragmentPayloadAttributeConfigurer configureFragmentAttribute(String attributeName);

    FragmentStructure getStructure();
}
