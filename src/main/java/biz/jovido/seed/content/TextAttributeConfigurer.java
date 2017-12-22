package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class TextAttributeConfigurer extends AttributeConfigurer<TextAttribute, TextAttributeConfigurer> {

    public TextAttributeConfigurer setMultiline(boolean multiline) {
        attribute.setMultiline(multiline);

        return this;
    }

    public TextAttributeConfigurer(StructureConfigurer structureConfigurer, TextAttribute attribute) {
        super(structureConfigurer, attribute);
    }
}
