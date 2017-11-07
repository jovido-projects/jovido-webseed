package biz.jovido.seed.thymeleaf.processor;

import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Stephan Grundner
 */
public class FieldIncludeProcessor extends AbstractFieldProcessor {

    private static final String ELEMENT_NAME = "include";

    public FieldIncludeProcessor(String dialectPrefix, int precedence) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ELEMENT_NAME, true, precedence, false);
    }
}
