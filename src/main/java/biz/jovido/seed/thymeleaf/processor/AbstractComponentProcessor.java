package biz.jovido.seed.thymeleaf.processor;

import biz.jovido.seed.TemplateResolver;
import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.Item;
import biz.jovido.seed.content.PayloadGroup;
import biz.jovido.seed.thymeleaf.TemplateNameResolver;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.NoOpToken;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractComponentProcessor extends AbstractAttributeTagProcessor {

    private Set<TemplateResolver> templateResolvers;

    public Set<TemplateResolver> getTemplateResolvers() {
        return templateResolvers;
    }

    public void setTemplateResolvers(Set<TemplateResolver> templateResolvers) {
        this.templateResolvers = templateResolvers;
    }

    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag,
                             final AttributeName currentAttributeName,
                             String attributeValue,
                             IElementTagStructureHandler structureHandler) {

        IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(context.getConfiguration());
        IStandardExpression expression = expressionParser.parseExpression(context, attributeValue);
        Object result = expression.execute(context);

        if (result == null || NoOpToken.class.isAssignableFrom(result.getClass())) {
            return;
        }

        if (result instanceof String) {
            throw new RuntimeException("[String] not supported");
        }

        String template;

        if (result instanceof HasTemplate) {
            template = ((HasTemplate) result).getTemplate();
        } else {
            template = getTemplateResolvers().stream()
                    .sorted(Comparator.comparingInt(TemplateResolver::getPrecedence))
                    .map(it -> it.resolveTemplate(result))
                    .filter(StringUtils::hasText)
                    .findFirst().orElse(null);
        }

        if (StringUtils.hasText(template)) {
            structureHandler.setLocalVariable("self", result);

            String newAttributeName = "th:" + currentAttributeName.getAttributeName();
            structureHandler.replaceAttribute(currentAttributeName, newAttributeName, template);
        }
    }

    protected AbstractComponentProcessor(TemplateMode templateMode, String dialectPrefix, String elementName, boolean prefixElementName, String attributeName, boolean prefixAttributeName, int precedence, boolean removeAttribute) {
        super(templateMode, dialectPrefix, elementName, prefixElementName, attributeName, prefixAttributeName, precedence, removeAttribute);
    }
}
