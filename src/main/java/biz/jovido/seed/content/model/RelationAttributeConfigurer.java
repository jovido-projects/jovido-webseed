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

    public RelationAttributeConfigurer addAcceptedStructure(String structureName) {
        attribute.getAcceptedStructureNames().add(structureName);

        return this;
    }

    public RelationAttributeConfigurer(StructureConfiguration builder, RelationAttribute attribute) {
        super(builder, attribute);
    }

}
