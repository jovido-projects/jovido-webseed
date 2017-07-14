package biz.jovido.seed.content;

import java.util.Arrays;

/**
 * @author Stephan Grundner
 */
public class FragmentAttributeConfigurer extends AttributeConfigurer<FragmentAttribute, FragmentAttributeConfigurer> {

    public FragmentAttributeConfigurer setEmbeddable(boolean embeddable) {
        attribute.setEmbeddable(embeddable);

        return this;
    }


    public FragmentAttributeConfigurer addStructureName(String structureName) {
        attribute.getStructureNames().add(structureName);

        return this;
    }

    public FragmentAttributeConfigurer addStructureNames(String... structureNames) {
        attribute.getStructureNames().addAll(Arrays.asList(structureNames));

        return this;
    }

    public FragmentAttributeConfigurer(StructureBuilder builder, FragmentAttribute attribute) {
        super(builder, attribute);
    }
}
