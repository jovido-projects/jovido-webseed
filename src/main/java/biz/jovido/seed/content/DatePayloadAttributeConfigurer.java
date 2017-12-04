package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class DatePayloadAttributeConfigurer extends ValuePayloadAttributeConfigurer<DatePayloadAttribute> {

    public DatePayloadAttributeConfigurer(FragmentStructureConfigurer structureConfigurer, DatePayloadAttribute attribute) {
        super(structureConfigurer, attribute);
    }
}
