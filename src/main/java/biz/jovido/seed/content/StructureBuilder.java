package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class StructureBuilder implements StructureConfigurer {

    private final Structure structure;

    @Override
    public Structure getStructure() {
        return structure;
    }

    @Override
    public TextAttributeConfigurer addTextAttribute(String name) {
        TextAttribute attribute = new TextAttribute(name);
        structure.putAttribute(attribute);
        return new TextAttributeConfigurer(this, attribute);
    }

    @Override
    public RelationAttributeConfigurer addRelationAttribute(String name) {
        RelationAttribute attribute = new RelationAttribute(name);
        structure.putAttribute(attribute);
        return new RelationAttributeConfigurer(this, attribute);
    }

    public StructureBuilder(Structure structure) {
        this.structure = structure;
    }
}
