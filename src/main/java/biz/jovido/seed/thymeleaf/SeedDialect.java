package biz.jovido.seed.thymeleaf;

import biz.jovido.seed.thymeleaf.processor.SeedIncludeProcessor;
import biz.jovido.seed.thymeleaf.processor.SeedReplaceProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class SeedDialect extends AbstractProcessorDialect {

    private static final String PREFIX = "seed";

    public SeedReplaceProcessor createReplaceProcessor() {
        return new SeedReplaceProcessor(PREFIX, getDialectProcessorPrecedence() * 10);
    }

    public SeedIncludeProcessor createIncludeProcessor() {
        return new SeedIncludeProcessor(PREFIX, getDialectProcessorPrecedence() * 10 + 1);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        HashSet<IProcessor> processors = new HashSet<>();
        processors.add(createReplaceProcessor());
        processors.add(createIncludeProcessor());

        return processors;
    }

    public SeedDialect() {
        super("Seed Dialect", PREFIX, 1000);
    }
}
