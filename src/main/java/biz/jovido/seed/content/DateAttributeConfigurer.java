package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class DateAttributeConfigurer extends AttributeConfigurer<DateAttribute, DateAttributeConfigurer> {

    public DateAttributeConfigurer setFormat(String format) {
        attribute.setFormat(format);

        return this;
    }

    public DateAttributeConfigurer(StructureConfigurer builder, DateAttribute attribute) {
        super(builder, attribute);
    }

}
