package biz.jovido.seed.content;

import biz.jovido.seed.content.attribute.ItemAttribute;

/**
 * @author Stephan Grundner
 */
public class ItemAttributeConfigurer extends AttributeConfigurer<ItemAttribute, ItemAttributeConfigurer> {

    @Override
    protected ItemAttribute createAttribute(String name) {
        return new ItemAttribute(name);
    }

    public ItemAttributeConfigurer(String name, StructureBuilder builder) {
        super(name, builder);
    }
}
