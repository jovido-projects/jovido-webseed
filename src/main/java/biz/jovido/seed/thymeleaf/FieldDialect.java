package biz.jovido.seed.thymeleaf;

import biz.jovido.seed.thymeleaf.processor.FieldIncludeProcessor;
import biz.jovido.seed.thymeleaf.processor.FieldReplaceProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class FieldDialect extends AbstractProcessorDialect {

    private static final String PREFIX = "field";

    public FieldReplaceProcessor createReplaceProcessor() {
        return new FieldReplaceProcessor(PREFIX, getDialectProcessorPrecedence() * 10);
    }

    public FieldIncludeProcessor createIncludeProcessor() {
        return new FieldIncludeProcessor(PREFIX, getDialectProcessorPrecedence() * 10 + 1);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        HashSet<IProcessor> processors = new HashSet<>();
        processors.add(createReplaceProcessor());
        processors.add(createIncludeProcessor());

        return processors;
    }

    public FieldDialect() {
        super("Field Dialect", PREFIX, 900);
    }
}
