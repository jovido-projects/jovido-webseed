package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class ValuePayloadAttributeConfigurer<A extends ValuePayloadAttribute> extends PayloadAttributeConfigurer<A> {

    public ValuePayloadAttributeConfigurer(FragmentStructureConfigurer structureConfigurer, A attribute) {
        super(structureConfigurer, attribute);
    }
}
