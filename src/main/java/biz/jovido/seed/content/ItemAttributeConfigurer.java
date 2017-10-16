package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class ItemAttributeConfigurer extends AttributeConfigurer<ItemAttribute, ItemAttributeConfigurer> {

    public ItemAttributeConfigurer addAcceptedType(String name) {
        attribute.getAcceptedTypeNames().add(name);

        return this;
    }

    public ItemAttributeConfigurer(TypeConfigurer builder, ItemAttribute attribute) {
        super(builder, attribute);
    }
}
