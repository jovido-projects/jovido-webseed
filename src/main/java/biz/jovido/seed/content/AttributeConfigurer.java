package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class AttributeConfigurer<A extends Attribute, C extends AttributeConfigurer<A, C>> implements StructureConfiguration {

    protected final StructureConfigurer structureConfigurer;
    protected final A attribute;

    @SuppressWarnings("unchecked")
    public C setRequired(int required) {
        attribute.setRequired(required);

        return (C) this;
    }

    @SuppressWarnings("unchecked")
    public C setCapacity(int capacity) {
        attribute.setCapacity(capacity);

        return (C) this;
    }

    @Override
    public TextAttributeConfigurer configureTextAttribute(String attributeName) {
        return structureConfigurer.configureTextAttribute(attributeName);
    }

    @Override
    public DateAttributeConfigurer configureDateAttribute(String attributeName) {
        return structureConfigurer.configureDateAttribute(attributeName);
    }

    @Override
    public FragmentAttributeConfigurer configureFragmentAttribute(String attributeName) {
        return structureConfigurer.configureFragmentAttribute(attributeName);
    }

    @Override
    public Structure getStructure() {
        return structureConfigurer.getStructure();
    }

    protected AttributeConfigurer(StructureConfigurer structureConfigurer, A attribute) {
        this.structureConfigurer = structureConfigurer;
        this.attribute = attribute;
    }
}