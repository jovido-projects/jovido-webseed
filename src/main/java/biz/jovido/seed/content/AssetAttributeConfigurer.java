package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class AssetAttributeConfigurer<A extends AssetAttribute, C extends AssetAttributeConfigurer<A, C>> extends AttributeConfigurer<A, C> {

    public AssetAttributeConfigurer(StructureConfigurer builder, A attribute) {
        super(builder, attribute);
    }
}
