package biz.jovido.seed.thymeleaf.processor;

import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Stephan Grundner
 */
public class SeedReplaceProcessor extends AbstractSeedProcessor {

    private static final String ELEMENT_NAME = "replace";

    public SeedReplaceProcessor(String dialectPrefix, int precedence) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ELEMENT_NAME, true, precedence, false);
    }
}
