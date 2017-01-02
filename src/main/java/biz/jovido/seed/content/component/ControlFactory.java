package biz.jovido.seed.content.component;

/**
 * @author Stephan Grundner
 */
public interface ControlFactory {

    Control getControl(Class clazz, String propertyName, String nestedPath);
}
