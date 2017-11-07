package biz.jovido.seed.thymeleaf.processor;

import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Stephan Grundner
 */
public class ContentReplaceProcessor extends AbstractContentProcessor {

    private static final String ELEMENT_NAME = "replace";

    public ContentReplaceProcessor(String dialectPrefix, int precedence) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ELEMENT_NAME, true, precedence, false);
    }
}
