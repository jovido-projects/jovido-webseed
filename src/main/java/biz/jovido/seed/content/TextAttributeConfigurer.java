package biz.jovido.seed.content;

import biz.jovido.seed.content.attribute.TextAttribute;

/**
 * @author Stephan Grundner
 */
public class TextAttributeConfigurer extends AttributeConfigurer<TextAttribute, TextAttributeConfigurer> {

    public TextAttributeConfigurer setRows(int rows) {
        attribute.setRows(rows);

        return this;
    }

    @Override
    protected TextAttribute createAttribute(String name) {
        return new TextAttribute(name);
    }

    public TextAttributeConfigurer(String name, StructureBuilder builder) {
        super(name, builder);
    }
}
