package biz.jovido.seed.content;

import java.util.Arrays;

/**
 * @author Stephan Grundner
 */
public class ItemAttributeConfigurer extends AttributeConfigurer<ItemAttribute, ItemAttributeConfigurer> {

    public ItemAttributeConfigurer addAcceptedStructure(String name) {
        attribute.getAcceptedStructureNames().add(name);

        return this;
    }

    public ItemAttributeConfigurer addAcceptedStructure(String... names) {
        attribute.getAcceptedStructureNames().addAll(Arrays.asList(names));

        return this;
    }

    public ItemAttributeConfigurer(StructureConfigurer builder, ItemAttribute attribute) {
        super(builder, attribute);
    }
}
