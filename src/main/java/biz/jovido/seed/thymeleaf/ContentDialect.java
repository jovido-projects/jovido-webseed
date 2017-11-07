package biz.jovido.seed.thymeleaf;

import biz.jovido.seed.thymeleaf.processor.ContentIncludeProcessor;
import biz.jovido.seed.thymeleaf.processor.ContentReplaceProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class ContentDialect extends AbstractProcessorDialect {

    private static final String PREFIX = "seed";

    public ContentReplaceProcessor createReplaceProcessor() {
        return new ContentReplaceProcessor(PREFIX, getDialectProcessorPrecedence() * 10);
    }

    public ContentIncludeProcessor createIncludeProcessor() {
        return new ContentIncludeProcessor(PREFIX, getDialectProcessorPrecedence() * 10 + 1);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        HashSet<IProcessor> processors = new HashSet<>();
        processors.add(createReplaceProcessor());
        processors.add(createIncludeProcessor());

        return processors;
    }

    public ContentDialect() {
        super("Seed Dialect", PREFIX, 1000);
    }
}
