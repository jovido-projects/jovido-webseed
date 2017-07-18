package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class AttributeConfigurer<A extends Attribute, C extends AttributeConfigurer<A, C>> extends StructureConfigurer {

    protected final StructureBuilder builder;
    protected final A attribute;

    @SuppressWarnings("unchecked")
    public C setCapacity(int capacity) {
        attribute.setCapacity(capacity);

        return (C) this;
    }

    @SuppressWarnings("unchecked")
    public C setRequired(int required) {
        attribute.setRequired(required);

        return (C) this;
    }

    @Override
    public TextAttributeConfigurer addTextAttribute(String fieldName) {
        return builder.addTextAttribute(fieldName);
    }

    @Override
    public FragmentAttributeConfigurer addFragmentAttribute(String fieldName) {
        return builder.addFragmentAttribute(fieldName);
    }

    @Override
    public AssetAttributeConfigurer addAssetAttribute(String fieldName) {
        return builder.addAssetAttribute(fieldName);
    }

    @Override
    public void register() {
        builder.register();
    }

    public AttributeConfigurer(StructureBuilder builder, A attribute) {
        this.builder = builder;
        this.attribute = attribute;
    }
}
