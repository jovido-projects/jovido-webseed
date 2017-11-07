package biz.jovido.seed.thymeleaf;

import biz.jovido.seed.content.Item;

/**
 * @author Stephan Grundner
 */
public interface TemplateNameResolver {

    String resolveTemplateName(Item item);
}
