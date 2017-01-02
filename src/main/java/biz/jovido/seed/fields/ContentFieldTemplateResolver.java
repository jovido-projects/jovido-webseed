package biz.jovido.seed.fields;

import biz.jovido.seed.content.metamodel.Attribute;
import biz.jovido.seed.content.metamodel.FragmentType;
import biz.jovido.seed.content.metamodel.TextAttribute;
import biz.jovido.seed.content.model.Fragment;
import biz.jovido.seed.content.util.FragmentUtils;
import biz.jovido.spring.fields.twbs.Bootstrap4FieldTemplateResolver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.persistence.EntityManager;
import java.beans.PropertyDescriptor;

/**
 * @author Stephan Grundner
 */
@Component
public class ContentFieldTemplateResolver extends Bootstrap4FieldTemplateResolver {

    @Autowired
    EntityManager entityManager;

    @Override
    public int getPrecedence() {
        return 1000;
    }

    @Override
    public String resolveFieldTemplate(Class clazz, String propertyName) {
        String template = null;

        FragmentType fragmentType = FragmentUtils.fragmentType(clazz);
        Attribute attribute = fragmentType.getAttribute(propertyName);
        Assert.notNull(attribute, "Property not found: " + propertyName);
        if (attribute.isCollection()) {
            template = "bootstrap/field/collection";
        } else if (Fragment.class.isAssignableFrom(attribute.getRawType())) {
            template = "bootstrap/field/fragment";
        } else if (attribute instanceof TextAttribute) {

            TextAttribute textAttribute = (TextAttribute) attribute;
            switch (textAttribute.getTextType()) {
                case HTML:
                case XML:
                    template = "bootstrap/field/wysiwyg";
                    break;
                case PLAIN:
                default:
                    break;
            }

        }

        if (StringUtils.isEmpty(template)) {
            template = super.resolveFieldTemplate(clazz, propertyName);

            if (StringUtils.isEmpty(template)) {
                template = "bootstrap/field/default";
            }
        }

        return template;
    }
}
