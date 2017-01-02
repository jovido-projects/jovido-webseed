package biz.jovido.seed.content.component;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author Stephan Grundner
 */
@Primary
@Component("controlFactory")
public class DefaultControlFactory implements ControlFactory {

    @Override
    public Control getControl(Class clazz, String propertyName, String nestedPath) {
        Control control = new TextControl(nestedPath);
        control.setPropertyName(propertyName);

        return control;
    }
}
