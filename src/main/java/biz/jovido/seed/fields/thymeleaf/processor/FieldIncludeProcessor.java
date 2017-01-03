package biz.jovido.seed.fields.thymeleaf.processor;

import biz.jovido.seed.fields.thymeleaf.FieldsDialect;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Stephan Grundner
 */
public class FieldIncludeProcessor extends AbstractFieldProcessor {

    private static final String ELEMENT_NAME = "include";

    public FieldIncludeProcessor(FieldsDialect dialect, String dialectPrefix, int precedence) {
        super(dialect, TemplateMode.HTML, dialectPrefix, null, false, ELEMENT_NAME, true, precedence, false);
    }
}
