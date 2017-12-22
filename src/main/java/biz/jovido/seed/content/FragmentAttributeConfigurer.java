package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class FragmentAttributeConfigurer extends AttributeConfigurer<FragmentAttribute, FragmentAttributeConfigurer> {

    public FragmentAttributeConfigurer addAssignableStructures(String... structureNames) {
        StructureService structureService = structureConfigurer.structureService;
        for (String structureName : structureNames) {
            Structure structure = structureService.getStructure(structureName);
            attribute.addAssignableStructure(structure);
        }

        return this;
    }

    public FragmentAttributeConfigurer setPreferredStructure(String structureName) {
        Structure structure = structureConfigurer.structureService.getStructure(structureName);
        attribute.setPreferredStructure(structure);

        return this;
    }

    public FragmentAttributeConfigurer(StructureConfigurer structureConfigurer, FragmentAttribute attribute) {
        super(structureConfigurer, attribute);
    }
}
