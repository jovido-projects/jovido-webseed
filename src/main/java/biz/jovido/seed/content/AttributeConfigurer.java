package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class AttributeConfigurer<A extends Attribute, C extends AttributeConfigurer<A, C>> implements StructureConfiguration {

    protected final StructureConfigurer parentConfigurer;
    protected final A attribute;

    @Override
    public HierarchyConfigurer createHierarchy(String hierarchyName) {
        return parentConfigurer.createHierarchy(hierarchyName);
    }

    @Override
    public StructureConfigurer createStructure(String typeName, int revision) {
        return parentConfigurer.createStructure(typeName, revision);
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

    @Override
    public StructureConfigurer setPublishable(boolean publishable) {
        return parentConfigurer.setPublishable(publishable);
    }

    @Override
    public StructureConfigurer setLabelAttribute(String name) {
        return parentConfigurer.setLabelAttribute(name);
    }

    @Override
    public TextAttributeConfigurer addTextAttribute(String name) {
        return parentConfigurer.addTextAttribute(name);
    }

    @Override
    public ImageAttributeConfigurer addImageAttribute(String name) {
        return parentConfigurer.addImageAttribute(name);
    }

    @Override
    public ItemAttributeConfigurer addItemAttribute(String name) {
        return parentConfigurer.addItemAttribute(name);
    }

    @Override
    public LinkAttributeConfigurer addLinkAttribute(String name) {
        return parentConfigurer.addLinkAttribute(name);
    }

    public AttributeConfigurer(StructureConfigurer parentConfigurer, A attribute) {
        this.parentConfigurer = parentConfigurer;
        this.attribute = attribute;
    }
}
