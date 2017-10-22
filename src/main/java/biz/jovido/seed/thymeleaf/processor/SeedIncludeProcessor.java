package biz.jovido.seed.thymeleaf.processor;

import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Stephan Grundner
 */
public class SeedIncludeProcessor extends AbstractSeedProcessor {

    private static final String ELEMENT_NAME = "include";

    public SeedIncludeProcessor(String dialectPrefix, int precedence) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ELEMENT_NAME, true, precedence, false);
    }
}
