package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class RelationAttributeConfigurer extends AttributeConfigurer<RelationAttribute, RelationAttributeConfigurer> {


    public RelationAttributeConfigurer setCapacity(int capacity) {
        attribute.setCapacity(capacity);

        return this;
    }

    public RelationAttributeConfigurer setRequired(int required) {
        attribute.setRequired(required);

        return this;
    }

    public RelationAttributeConfigurer addAcceptedType(String name) {
        attribute.getAcceptedTypeNames().add(name);

        return this;
    }

    public RelationAttributeConfigurer(StructureConfigurer builder, RelationAttribute attribute) {
        super(builder, attribute);
    }

}
