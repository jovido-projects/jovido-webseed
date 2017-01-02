package biz.jovido.seed.content.util;

import biz.jovido.seed.content.metamodel.Attribute;
import biz.jovido.seed.content.metamodel.FragmentType;
import biz.jovido.seed.content.metamodel.ListAttribute;
import biz.jovido.seed.content.metamodel.TextAttribute;
import biz.jovido.seed.content.model.Content;
import biz.jovido.seed.content.model.Fragment;
import biz.jovido.seed.content.model.TextContent;
import biz.jovido.seed.content.model.TextType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author Stephan Grundner
 */
public final class FragmentUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FragmentUtils.class);

    public interface PropertyOrFieldCallback {

        void doWith(Member member, String name);
    }

    private static final Map<String, FragmentType> fragmentTypes = new ConcurrentHashMap<>();

    public static void doWithPropertyOrField(Class<? extends Fragment> clazz, PropertyOrFieldCallback callback) {
        Stream.of(BeanUtils.getPropertyDescriptors(clazz)).forEach(pd -> {
            Method readMethod = pd.getReadMethod();
            callback.doWith(readMethod, pd.getName());
        });

        ReflectionUtils.doWithFields(clazz, field -> {
            callback.doWith(field, field.getName());
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T instantiateFragment(String className, ClassLoader classLoader) {
        Class<?> clazz = ClassUtils.resolveClassName(className, classLoader);
        if (!Fragment.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Unexpected Class [" + className + "]");
        }
        return (T) BeanUtils.instantiate(clazz);
    }

    public static boolean isFragment(Class<?> clazz) {
        return clazz != null && Fragment.class.isAssignableFrom(clazz);
    }

    public static boolean isFragment(Object object) {
        return object != null && Fragment.class.isAssignableFrom(object.getClass());
    }

    public static <F extends Fragment> FragmentType<F> fragmentType(Class<F> rawType) {
        if (!Fragment.class.isAssignableFrom(rawType)) {
            throw new RuntimeException("Class [" + rawType.getName() + "] is not a fragment");
        }
        @SuppressWarnings("unchecked")
        FragmentType<F> fragmentType = (FragmentType<F>)
                fragmentTypes.get(rawType.getName());

        if (fragmentType == null) {
            fragmentType = buildFragmentType(rawType);
        }

        return fragmentType;
    }

    public static <F extends Fragment> FragmentType<F> buildFragmentType(Class<F> rawType) {
        FragmentType<F> fragmentType = new FragmentType<>(rawType);

        doWithPropertyOrField(rawType, ((member, name) -> {
            Annotation content = AnnotationUtils
                    .findAnnotation((AnnotatedElement) member, Content.class);

            if (content != null) {
                Attribute<F, ?> attribute;

                Type genericAttributeType;
                if (member instanceof Method) {
                    genericAttributeType = ((Method) member).getGenericReturnType();
                } else {
                    genericAttributeType = ((Field) member).getGenericType();
                }

                Type[] typeArguments;
                Class<?> type;

                if (genericAttributeType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericAttributeType;
                    type = (Class<?>) parameterizedType.getRawType();
                    typeArguments = parameterizedType.getActualTypeArguments();
                } else {
                    typeArguments = new Type[0];
                    type = (Class<?>) genericAttributeType;
                }

                if (List.class.isAssignableFrom(type)) {
                    Class<?> elementType;
                    if (typeArguments.length == 0) {
                        elementType = Object.class;
                    } else {
                        elementType = (Class<?>) typeArguments[0];
                    }
                    @SuppressWarnings("unchecked")
                    Class<List<?>> listType = (Class<List<?>>) type;
                    attribute = new ListAttribute<>(name, listType, elementType);
                } else if (String.class.isAssignableFrom(type)) {
                    TextContent textContent = AnnotationUtils
                            .findAnnotation((AnnotatedElement) member, TextContent.class);
                    TextType textType = textContent != null
                            ? textContent.value()
                            : TextType.PLAIN;
                    attribute = new TextAttribute<F>(name, textType);
                } else {

                    attribute = new Attribute<>(name, type);
                }

                Attribute<F, ?> existing = fragmentType.addAttribute(attribute);
                if (!ObjectUtils.isEmpty(existing)) {
                    LOG.warn("Attribute {} already mapped for class {}", name, rawType);
                }
            }
        }));

        return fragmentType;
    }

    private FragmentUtils() {
    }
}
