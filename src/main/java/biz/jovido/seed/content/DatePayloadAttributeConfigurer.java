package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class DatePayloadAttributeConfigurer extends PayloadAttributeConfigurer<DatePayloadAttribute, DatePayloadAttributeConfigurer> {

    public DatePayloadAttributeConfigurer(FragmentStructureConfigurer structureConfigurer, DatePayloadAttribute attribute) {
        super(structureConfigurer, attribute);
    }
}
