package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class AttributeConfigurer<A extends Attribute, C extends AttributeConfigurer<A, C>> implements StructureConfigurer {

    protected final StructureBuilder builder;
    protected final A attribute;

    @Override
    public Structure getStructure() {
        return builder.getStructure();
    }

    @Override
    public TextAttributeConfigurer addTextAttribute(String name) {
        return builder.addTextAttribute(name);
    }

    @Override
    public RelationAttributeConfigurer addRelationAttribute(String name) {
        return builder.addRelationAttribute(name);
    }

    public AttributeConfigurer(StructureBuilder builder, A attribute) {
        this.builder = builder;
        this.attribute = attribute;
    }
}
