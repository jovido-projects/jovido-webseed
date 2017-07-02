package biz.jovido.seed.content;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class StructureBuilder implements StructureConfigurer {

    public static StructureBuilder create(String name) {
        return new StructureBuilder(name);
    }

    private final String name;
    private int revision;

    private final List<AttributeConfigurer> attributeConfigurers = new ArrayList<>();

//    @Override
//    public StructureConfigurer setName(String name) {
//        this.name = name;
//
//        return this;
//    }

    @Override
    public StructureConfigurer setRevision(int revision) {
        this.revision = revision;

        return this;
    }

    private <T extends AttributeConfigurer<?, T>> T appendAttributeConfigurer(T attributeConfigurer) {
        attributeConfigurers.add(attributeConfigurer);

        return attributeConfigurer;
    }

    @Override
    public TextAttributeConfigurer addTextAttribute(String name) {
        return appendAttributeConfigurer(new TextAttributeConfigurer(name, this));
    }

    @Override
    public ItemAttributeConfigurer addItemAttribute(String name) {
        return appendAttributeConfigurer(new ItemAttributeConfigurer(name, this));
    }

    @Override
    public Structure build(StructureService structureService) {
        Structure structure = structureService.findStructureByName(name, revision);
        if (structure == null) {
            structure = new Structure();
            structure.setName(name);
            structure.setRevision(revision);
        }

        for (AttributeConfigurer attributeConfigurer : attributeConfigurers) {
            attributeConfigurer.applyTo(structure);
        }

        return structureService.saveStructure(structure);
    }

    public StructureBuilder(String name) {
        this.name = name;
    }
}
