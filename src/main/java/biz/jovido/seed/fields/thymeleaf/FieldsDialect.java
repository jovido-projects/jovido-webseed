package biz.jovido.seed.fields.thymeleaf;

import biz.jovido.seed.fields.FieldTemplateResolver;
import biz.jovido.seed.fields.thymeleaf.processor.FieldIncludeProcessor;
import biz.jovido.seed.fields.thymeleaf.processor.FieldReplaceProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * Thymeleaf fields dialect.
 *
 * Usage example:
 * <code>
 *   @Autowired SpringTemplateEngine templateEngine;
 *   @Autowired ListableBeanFactory beanFactory;
 *
 *   @PostConstruct
 *   void init() {
 *     templateEngine.addDialect(new FieldsDialect(beanFactory));
 *   }
 * </code>
 * @author Stephan Grundner
 */
public class FieldsDialect extends AbstractProcessorDialect {

    private static final String PREFIX = "field";

//    private final ListableBeanFactory beanFactory;
    private FieldTemplateResolver fieldTemplateResolver;

//    private Set<FieldTemplateResolver> fieldResolvers = new HashSet<>();

    public FieldReplaceProcessor createFieldReplaceProcessor() {
        return new FieldReplaceProcessor(this, PREFIX, getDialectProcessorPrecedence() * 10);
    }

    public FieldIncludeProcessor createFieldIncludeProcessor() {
        return new FieldIncludeProcessor(this, PREFIX, getDialectProcessorPrecedence() * 10 + 1);
    }

//    public boolean addFieldTemplateResolver(FieldTemplateResolver fieldResolver) {
//        return fieldResolvers.add(fieldResolver);
//    }
//
//    public boolean removeFieldTemplateResolver(FieldTemplateResolver fieldResolver) {
//        return fieldResolvers.remove(fieldResolver);
//    }

    public FieldTemplateResolver getFieldTemplateResolver() {
        return fieldTemplateResolver;
    }

    public void setFieldTemplateResolver(FieldTemplateResolver fieldTemplateResolver) {
        this.fieldTemplateResolver = fieldTemplateResolver;
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        HashSet<IProcessor> processors = new HashSet<>();
        processors.add(createFieldReplaceProcessor());
        processors.add(createFieldIncludeProcessor());

        return processors;
    }

//    public List<FieldTemplateResolver> getFieldResolvers() {
//        return fieldResolvers.stream()
//                .sorted((x,y) -> Integer.compare(x.getPrecedence(), y.getPrecedence()))
//                .collect(Collectors.toList());
//    }

//    public FieldsDialect(ListableBeanFactory beanFactory, final int processorPrecedence) {
    public FieldsDialect(final int processorPrecedence) {
        super("Fields Dialect", PREFIX, processorPrecedence);

//        Assert.notNull(beanFactory);
//
//        this.beanFactory = beanFactory;
//
//        Map<String, FieldTemplateResolver> fieldTemplateResolverByBeanName =
//                beanFactory.getBeansOfType(FieldTemplateResolver.class);
//        fieldResolvers.addAll(fieldTemplateResolverByBeanName.values());
    }

//    public FieldsDialect(ListableBeanFactory beanFactory) {
    public FieldsDialect() {
//        this(beanFactory, 1000);
        this(1000);
    }
}
