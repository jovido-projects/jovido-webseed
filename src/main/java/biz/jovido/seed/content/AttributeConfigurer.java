package biz.jovido.seed.content;

import org.springframework.beans.BeanUtils;

/**
 * @author Stephan Grundner
 */
public abstract class AttributeConfigurer<A extends Attribute, C extends AttributeConfigurer<A, C>> implements StructureConfigurer {

    private final StructureBuilder builder;
    protected final A attribute;

    protected abstract A createAttribute(String name);

    @Override
    public StructureConfigurer setRevision(int revision) {
        return builder.setRevision(revision);
    }

    @Override
    public TextAttributeConfigurer addTextAttribute(String name) {
        return builder.addTextAttribute(name);
    }

    @Override
    public ItemAttributeConfigurer addItemAttribute(String name) {
        return builder.addItemAttribute(name);
    }

    @Override
    public Structure build(StructureService structureService) {
        return builder.build(structureService);
    }

    @SuppressWarnings("unchecked")
    protected C self() {
        return (C) this;
    }

    public C setCapacity(int capacity) {
        attribute.setCapacity(capacity);

        return self();
    }

    public C setRequired(int required) {
        attribute.setRequired(required);

        return self();
    }

    public C applyTo(Structure structure) {

        Attribute existing = structure.getAttribute(attribute.getName());
        if (existing == null) {
            existing = attribute;
            structure.addAttribute(existing);
        } else {

            if (attribute.getClass() != existing.getClass()) {
                throw new RuntimeException("Cannot change attribute type");
            }

            BeanUtils.copyProperties(attribute, existing, "id");
        }

        return self();
    }

    public AttributeConfigurer(String name, StructureBuilder builder) {
        this.builder = builder;

        attribute = createAttribute(name);
    }
}
