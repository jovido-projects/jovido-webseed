package biz.jovido.seed.content.model;

import biz.jovido.seed.content.StructureService;

/**
 * @author Stephan Grundner
 */
public class StructureConfiguration implements StructureConfigurer {

    private final Structure structure;
    private final StructureService structureService;

    public Structure getStructure() {
        return structure;
    }

    public TextAttributeConfigurer addTextAttribute(String name) {
        TextAttribute attribute = (TextAttribute) structure.getAttribute(name);
        if (attribute == null) {
            attribute = new TextAttribute();
            structure.setAttribute(name, attribute);
        }

        return new TextAttributeConfigurer(this, attribute);
    }

    public RelationAttributeConfigurer addRelationAttribute(String name) {
        RelationAttribute attribute = (RelationAttribute) structure.getAttribute(name);
        if (attribute == null) {
            attribute = new RelationAttribute();
            structure.setAttribute(name, attribute);
        }

        return new RelationAttributeConfigurer(this, attribute);
    }

    public StructureConfiguration(Structure structure, StructureService structureService) {
        this.structure = structure;
        this.structureService = structureService;
    }
}
