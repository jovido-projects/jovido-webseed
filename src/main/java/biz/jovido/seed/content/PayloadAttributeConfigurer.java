package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class PayloadAttributeConfigurer<A extends PayloadAttribute> implements FragmentStructureConfiguration {

    protected final FragmentStructureConfigurer structureConfigurer;
    protected final A attribute;

    @Override
    public TextPayloadAttributeConfigurer configureTextAttribute(String attributeName) {
        return structureConfigurer.configureTextAttribute(attributeName);
    }

    @Override
    public DatePayloadAttributeConfigurer configureDateAttribute(String attributeName) {
        return structureConfigurer.configureDateAttribute(attributeName);
    }

    @Override
    public FragmentPayloadAttributeConfigurer configureFragmentAttribute(String attributeName) {
        return structureConfigurer.configureFragmentAttribute(attributeName);
    }

    @Override
    public FragmentStructure getStructure() {
        return structureConfigurer.getStructure();
    }

    protected PayloadAttributeConfigurer(FragmentStructureConfigurer structureConfigurer, A attribute) {
        this.structureConfigurer = structureConfigurer;
        this.attribute = attribute;
    }
}