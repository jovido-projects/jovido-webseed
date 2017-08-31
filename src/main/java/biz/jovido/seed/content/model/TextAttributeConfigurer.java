package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class TextAttributeConfigurer extends AttributeConfigurer<TextAttribute, TextAttributeConfigurer> {

    public TextAttributeConfigurer setLines(int lines) {
        attribute.setLines(lines);

        return this;
    }

    public TextAttributeConfigurer(StructureConfigurer builder, TextAttribute attribute) {
        super(builder, attribute);
    }

}
