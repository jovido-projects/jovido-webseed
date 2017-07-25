package biz.jovido.seed.content;

import java.util.Arrays;

/**
 * @author Stephan Grundner
 */
public class RelationAttributeConfigurer extends AttributeConfigurer<RelationAttribute, RelationAttributeConfigurer> {

    public RelationAttributeConfigurer addStructureName(String structureName) {
        attribute.getStructureNames().add(structureName);

        return this;
    }

    public RelationAttributeConfigurer addStructureNames(String... structureNames) {
        attribute.getStructureNames().addAll(Arrays.asList(structureNames));

        return this;
    }

    public RelationAttributeConfigurer setType(Relation.Type type) {
        attribute.setType(type);

        return this;
    }

    public RelationAttributeConfigurer(StructureBuilder builder, RelationAttribute attribute) {
        super(builder, attribute);
    }

}
