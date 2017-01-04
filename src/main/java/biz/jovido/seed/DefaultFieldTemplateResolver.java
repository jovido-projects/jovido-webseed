package biz.jovido.seed;

import biz.jovido.seed.content.domain.Fragment;
import biz.jovido.seed.fields.FieldTemplate;
import biz.jovido.seed.fields.FieldTemplateResolver;
import biz.jovido.seed.util.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class DefaultFieldTemplateResolver implements FieldTemplateResolver {

    @Override
    public String resolveFieldTemplate(Class clazz, String propertyName) {
        PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, propertyName);
        Class<?> propertyType = pd.getPropertyType();
        Assert.notNull(propertyType, String.format("Property type of [{}] must not be null", propertyName));

        String template = null;

//        Try to resolve template by annotations
        FieldTemplate fieldTemplateAnnotation = PropertyUtils.findAnnotation(pd, FieldTemplate.class);
        if (fieldTemplateAnnotation != null) {
            template = fieldTemplateAnnotation.template();
        }

//        Try to resolve template by property type
        if (StringUtils.isEmpty(template)) {
            if (String.class.isAssignableFrom(propertyType)) {
                template = "field/text";
            } else if (Number.class.isAssignableFrom(propertyType)) {
                template = "field/number";
            } else if (Boolean.class.isAssignableFrom(propertyType)) {
                template = "field/boolean";
            } else if (Date.class.isAssignableFrom(propertyType)) {
                template = "field/date";
            } else {

                if (List.class == propertyType) {
                    return "field/list";
                } else if (Fragment.class.isAssignableFrom(propertyType)) {
                    return "field/fragment/reference";
                } else {
                    template = null;
                }
            }
        }

        if (StringUtils.isEmpty(template)) {

        }

        return template;
    }
}
