package biz.jovido.seed.content;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;

/**
 * @author Stephan Grundner
 */
public class FragmentStructureConfigurer implements FragmentStructureConfiguration {

    protected final FragmentStructureService structureService;
    private final FragmentStructure structure;

    @SuppressWarnings("unchecked")
    private <A extends PayloadAttribute, C extends PayloadAttributeConfigurer<A>> C configureAttribute(String attributeName, Class<A> attributeType, Class<C> configurerType) {
        A attribute = (A) structure.getAttribute(attributeName);
        if (attribute == null) {
            attribute = BeanUtils.instantiate(attributeType);
            structure.setAttribute(attributeName, attribute);
        }

        try {
            Constructor<C> constructor = configurerType.getDeclaredConstructor(FragmentStructureConfigurer.class, attributeType);
            C configurer = BeanUtils.instantiateClass(constructor, this, attribute);

            return configurer;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TextPayloadAttributeConfigurer configureTextAttribute(String attributeName) {
        return configureAttribute(attributeName, TextPayloadAttribute.class, TextPayloadAttributeConfigurer.class);
    }

    @Override
    public DatePayloadAttributeConfigurer configureDateAttribute(String attributeName) {
        return configureAttribute(attributeName, DatePayloadAttribute.class, DatePayloadAttributeConfigurer.class);
    }

    @Override
    public FragmentPayloadAttributeConfigurer configureFragmentAttribute(String attributeName) {
        return configureAttribute(attributeName, FragmentPayloadAttribute.class, FragmentPayloadAttributeConfigurer.class);
    }

    @Override
    public FragmentStructure getStructure() {
        return structure;
    }

    public FragmentStructureConfigurer(FragmentStructureService structureService, FragmentStructure structure) {
        this.structureService = structureService;
        this.structure = structure;
    }
}
