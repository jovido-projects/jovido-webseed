package biz.jovido.seed;

/**
 * @author Stephan Grundner
 */
public interface TemplateResolver {

    String resolveTemplate(Object component);

    int getPrecedence();
}
