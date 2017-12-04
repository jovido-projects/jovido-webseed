package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class TextPayloadAttributeConfigurer extends ValuePayloadAttributeConfigurer<TextPayloadAttribute> {

    public TextPayloadAttributeConfigurer setMultiline(boolean multiline) {
        attribute.setMultiline(multiline);

        return this;
    }

    public TextPayloadAttributeConfigurer(FragmentStructureConfigurer structureConfigurer, TextPayloadAttribute attribute) {
        super(structureConfigurer, attribute);
    }
}
