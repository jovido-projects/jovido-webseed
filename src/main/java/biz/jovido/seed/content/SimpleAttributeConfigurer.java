package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class SimpleAttributeConfigurer<A extends SimpleAttribute, C extends SimpleAttributeConfigurer<A, C>>
        extends AttributeConfigurer<A, SimpleAttributeConfigurer<A, C>> {

    public SimpleAttributeConfigurer(StructureBuilder builder, A attribute) {
        super(builder, attribute);
    }
}
