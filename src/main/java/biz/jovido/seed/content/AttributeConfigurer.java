package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class AttributeConfigurer<A extends Attribute, C extends AttributeConfigurer<A, C>> implements StructureConfigurer {

    protected final StructureConfiguration configuration;
    protected final A attribute;

    @Override
    public Structure getStructure() {
        return configuration.getStructure();
    }

    @Override
    public TextAttributeConfigurer addTextAttribute(String name) {
        return configuration.addTextAttribute(name);
    }

    public RelationAttributeConfigurer addRelationAttribute(String name) {
        return configuration.addRelationAttribute(name);
    }

    public AttributeConfigurer(StructureConfiguration configuration, A attribute) {
        this.configuration = configuration;
        this.attribute = attribute;
    }
}
