package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class AttributeConfigurer<A extends Attribute, C extends AttributeConfigurer<A, C>> implements Configuration {

    protected final StructureConfigurer parentConfigurer;
    protected final A attribute;

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

    @SuppressWarnings("unchecked")
    public C setCapacity(int capacity) {
        attribute.setCapacity(capacity);

        return (C) this;
    }

    @SuppressWarnings("unchecked")
    public C setRequired(int required) {
        attribute.setRequired(required);

        return (C) this;
    }

    public TextAttributeConfigurer addTextAttribute(String name) {
        return parentConfigurer.addTextAttribute(name);
    }

    public RelationAttributeConfigurer addRelationAttribute(String name) {
        return parentConfigurer.addRelationAttribute(name);
    }

    public StructureConfigurer setStandalone(boolean standalone) {
        return parentConfigurer.setStandalone(standalone);
    }

    public StructureConfigurer addAcceptedHierarchy(String name) {
        return parentConfigurer.addAcceptedHierarchy(name);
    }

    public AttributeConfigurer(StructureConfigurer parentConfigurer, A attribute) {
        this.parentConfigurer = parentConfigurer;
        this.attribute = attribute;
    }
}
