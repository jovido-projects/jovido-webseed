package biz.jovido.seed.fields;

/**
 * @author Stephan Grundner
 */
public interface FieldTemplateResolver {

    String resolveFieldTemplate(Class clazz, String propertyName);
}
