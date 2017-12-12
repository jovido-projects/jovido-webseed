package biz.jovido.seed.content.structure;

/**
 * @author Stephan Grundner
 */
public class FragmentAttributeConfigurer extends AttributeConfigurer<FragmentAttribute, FragmentAttributeConfigurer> {

    public FragmentAttributeConfigurer addAssignableStructureNames(String... structureNames) {
        StructureService structureService = structureConfigurer.structureService;
        for (String structureName : structureNames) {
            Structure structure = structureService.getStructure(structureName);
            attribute.addAssignableStructure(structure);
        }

        return this;
    }

    public FragmentAttributeConfigurer(StructureConfigurer structureConfigurer, FragmentAttribute attribute) {
        super(structureConfigurer, attribute);
    }
}
