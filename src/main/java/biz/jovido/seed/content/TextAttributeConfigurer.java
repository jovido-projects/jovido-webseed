package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class TextAttributeConfigurer extends AttributeConfigurer<TextAttribute, TextAttributeConfigurer> {

    public TextAttributeConfigurer setRows(int rows) {
        attribute.setRows(rows);

        return this;
    }

    public TextAttributeConfigurer(StructureBuilder builder, TextAttribute attribute) {
        super(builder, attribute);
    }
}
