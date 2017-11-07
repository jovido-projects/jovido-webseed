package biz.jovido.seed.thymeleaf.processor;

import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Stephan Grundner
 */
public class FieldReplaceProcessor extends AbstractFieldProcessor {

    private static final String ELEMENT_NAME = "replace";

    public FieldReplaceProcessor(String dialectPrefix, int precedence) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ELEMENT_NAME, true, precedence, false);
    }
}
