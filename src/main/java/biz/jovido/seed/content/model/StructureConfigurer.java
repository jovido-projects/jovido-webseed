package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class StructureConfigurer implements Configuration {

    private final Configurer parentConfigurer;
    private final Structure structure;

    @Override
    public HierarchyConfigurer forHierarchy(String hierarchyName) {
        return parentConfigurer.forHierarchy(hierarchyName);
    }

    @Override
    public StructureConfigurer forStructure(String typeName, int revision) {
        return parentConfigurer.forStructure(typeName, revision);
    }

    @Override
    public void apply() {
        parentConfigurer.apply();
    }

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

    public StructureConfigurer setStandalone(boolean standalone) {
        structure.setStandalone(standalone);

        return this;
    }

    public StructureConfigurer addAcceptedHierarchy(String name) {
        structure.getAcceptedHierarchyNames().add(name);

        return this;
    }

    public StructureConfigurer setLabelName(String labelName) {
        structure.setLabelName(labelName);

        return this;
    }

    public StructureConfigurer(Configurer parentConfigurer, Structure structure) {
        this.parentConfigurer = parentConfigurer;
        this.structure = structure;
    }
}
