package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class FragmentPayloadAttributeConfigurer extends PayloadAttributeConfigurer<FragmentPayloadAttribute> {

    public FragmentPayloadAttributeConfigurer addAssignableStructureNames(String... structureNames) {
        FragmentStructureService structureService = structureConfigurer.structureService;
        for (String structureName : structureNames) {
            FragmentStructure structure = structureService.getStructure(structureName);
            attribute.addAssignableStructure(structure);
        }

        return this;
    }

    public FragmentPayloadAttributeConfigurer(FragmentStructureConfigurer structureConfigurer, FragmentPayloadAttribute attribute) {
        super(structureConfigurer, attribute);
    }
}
