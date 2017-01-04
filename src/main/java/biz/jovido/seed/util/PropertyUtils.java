package biz.jovido.seed.util;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public final class PropertyUtils {

    public static <T extends Annotation> T findAnnotation(PropertyDescriptor propertyDescriptor, Class<T> annotationType) {
        Method getter = propertyDescriptor.getReadMethod();

        T annotation = AnnotationUtils.findAnnotation(getter, annotationType);
        if (annotation == null) {
            Class<?> declaringClass = getter.getDeclaringClass();
            Field field = ReflectionUtils.findField(declaringClass, propertyDescriptor.getName());
            if (field != null && field.getType() == propertyDescriptor.getPropertyType()) {
                annotation = AnnotationUtils.findAnnotation(field, annotationType);
            }
        }

        return annotation;
    }

    public static boolean hasAnnotation(PropertyDescriptor propertyDescriptor, Class<? extends Annotation> annotationType) {
        return findAnnotation(propertyDescriptor, annotationType) != null;
    }

    private static Type getGenericType(Method getter, Method setter) {
        Type genericType = null;

        if (getter != null) {
            genericType = getter.getGenericReturnType();
            if (genericType == null && setter != null) {
                Type[] genericParameterTypes = setter.getGenericParameterTypes();
                if (genericParameterTypes.length == 1) {
                    return genericParameterTypes[0];
                }
            }
        }

        return genericType;
    }

    public static Type getGenericType(PropertyDescriptor propertyDescriptor) {
        return getGenericType(propertyDescriptor.getReadMethod(), propertyDescriptor.getWriteMethod());
    }

    public static ParameterizedType getParameterizedType(PropertyDescriptor propertyDescriptor) {
        Type genericType = getGenericType(propertyDescriptor);
        if (genericType instanceof ParameterizedType) {
            return (ParameterizedType) genericType;
        }

        return null;
    }

    public static Class<?> getListElementType(PropertyDescriptor propertyDescriptor) {
        Class<?> propertyType = propertyDescriptor.getPropertyType();
        Assert.isTrue(List.class == propertyType);

        Class<?> elementType = Object.class;

        Type type = getGenericType(propertyDescriptor);
        if (type != null) {
            Map<TypeVariable<?>, Type> typeArguments = TypeUtils.getTypeArguments(type, List.class);
            if (!typeArguments.isEmpty()) {
                elementType = (Class<?>) typeArguments.values()
                        .stream()
                        .findFirst()
                        .orElse(elementType);
            }
        }

        return elementType;
    }

    public static Class<?> getDeclaringClass(PropertyDescriptor propertyDescriptor) {
        Class<?> declaringClass = null;

        Method getter = propertyDescriptor.getReadMethod();
        if (getter != null) {
            declaringClass = getter.getDeclaringClass();
            if (declaringClass == null) {
                Method setter = propertyDescriptor.getWriteMethod();
                if (setter != null) {
                    declaringClass = setter.getDeclaringClass();
                }
            }
        }

        return declaringClass;
    }

    private PropertyUtils() {}
}
