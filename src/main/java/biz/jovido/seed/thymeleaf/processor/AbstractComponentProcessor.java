package biz.jovido.seed.thymeleaf.processor;

import biz.jovido.seed.component.HasTemplate;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.NoOpToken;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractComponentProcessor extends AbstractAttributeTagProcessor {

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
            throw new TemplateProcessingException("[String] not supported");
        }

        String template;

        if (result instanceof HasTemplate) {
            template = ((HasTemplate) result).getTemplate();
        } else {
            throw new RuntimeException();
        }

        if (StringUtils.isEmpty(template)) {
            throw new TemplateProcessingException(String.format("Unable to resolve template for expression %s",
                    expression.getStringRepresentation()));
        }

        structureHandler.setLocalVariable("self", result);
        String newAttributeName = "th:" + currentAttributeName.getAttributeName();
        structureHandler.replaceAttribute(currentAttributeName, newAttributeName, template);
    }

    protected AbstractComponentProcessor(TemplateMode templateMode, String dialectPrefix, String elementName, boolean prefixElementName, String attributeName, boolean prefixAttributeName, int precedence, boolean removeAttribute) {
        super(templateMode, dialectPrefix, elementName, prefixElementName, attributeName, prefixAttributeName, precedence, removeAttribute);
    }
}
