package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.Content;
import biz.jovido.seed.content.model.ContentTypeOptions;
import biz.jovido.seed.util.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Stephan Grundner
 */
@Service
public class ContentService {

    public String getMessageCode(PropertyDescriptor propertyDescriptor) {
        Class<?> declaringClass = PropertyUtils.getDeclaringClass(propertyDescriptor);
        return declaringClass.getName() + "." + propertyDescriptor.getName();
    }

    public String getMessageCode(Class<?> clazz, String propertyName) {
        PropertyDescriptor propertyDescriptor = BeanUtils
                .getPropertyDescriptor(clazz, propertyName);
        return getMessageCode(propertyDescriptor);
    }

    public Class<?> getListElementType(Class<?> clazz, String propertyName) {
        PropertyDescriptor propertyDescriptor = BeanUtils
                .getPropertyDescriptor(clazz, propertyName);
        return PropertyUtils.getListElementType(propertyDescriptor);
    }

    public List<String> getContentProperties(Class<?> clazz) {
        return Stream.of(BeanUtils.getPropertyDescriptors(clazz))
                .filter(pd -> PropertyUtils.hasAnnotation(pd, Content.class))
                .map(PropertyDescriptor::getName)
                .collect(Collectors.toList());
    }

    public List<Class<?>> getContentTypes(PropertyDescriptor propertyDescriptor) {
        ContentTypeOptions contentTypeAnnotation = PropertyUtils.findAnnotation(propertyDescriptor, ContentTypeOptions.class);
        if (contentTypeAnnotation != null) {
            return Stream.of(contentTypeAnnotation.classes())
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}
