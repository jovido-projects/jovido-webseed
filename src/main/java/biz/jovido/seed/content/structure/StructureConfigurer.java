package biz.jovido.seed.content.structure;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;

/**
 * @author Stephan Grundner
 */
public class StructureConfigurer implements StructureConfiguration {

    protected final StructureService structureService;
    private final Structure structure;

    @SuppressWarnings("unchecked")
    private <A extends Attribute, C extends AttributeConfigurer<A, C>> C configureAttribute(String attributeName, Class<A> attributeType, Class<C> configurerType) {
        A attribute = (A) structure.getAttribute(attributeName);
        if (attribute == null) {
            attribute = BeanUtils.instantiate(attributeType);
            structure.setAttribute(attributeName, attribute);
        }

        try {
            Constructor<C> constructor = configurerType.getDeclaredConstructor(StructureConfigurer.class, attributeType);
            C configurer = BeanUtils.instantiateClass(constructor, this, attribute);

            return configurer;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TextAttributeConfigurer configureTextAttribute(String attributeName) {
        return configureAttribute(attributeName, TextAttribute.class, TextAttributeConfigurer.class);
    }

    @Override
    public DateAttributeConfigurer configureDateAttribute(String attributeName) {
        return configureAttribute(attributeName, DateAttribute.class, DateAttributeConfigurer.class);
    }

    @Override
    public FragmentAttributeConfigurer configureFragmentAttribute(String attributeName) {
        return configureAttribute(attributeName, FragmentAttribute.class, FragmentAttributeConfigurer.class);
    }

    @Override
    public Structure getStructure() {
        return structure;
    }

    public StructureConfigurer(StructureService structureService, Structure structure) {
        this.structureService = structureService;
        this.structure = structure;
    }
}
