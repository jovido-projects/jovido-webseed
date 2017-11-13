package biz.jovido.seed.component;

import biz.jovido.seed.TemplateResolver;
import org.springframework.util.ClassUtils;

/**
 * @author Stephan Grundner
 */
public class ClassNameTemplateResolver implements TemplateResolver {

    @Override
    public String resolveTemplate(Object component) {
        if (component != null) {
            return ClassUtils.getShortNameAsProperty(component.getClass());
        }

        return null;
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }
}
