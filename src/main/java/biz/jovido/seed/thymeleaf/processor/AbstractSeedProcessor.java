package biz.jovido.seed.thymeleaf.processor;

import biz.jovido.seed.content.Item;
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

/**
 * @author Stephan Grundner
 */
public abstract class AbstractSeedProcessor extends AbstractAttributeTagProcessor {

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

        if (result instanceof Item) {
//            String template = annotation.template();
//            structureHandler.setLocalVariable("component", result);

//            BeanWrapper resultWrapper = new BeanWrapperImpl(result);
//            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(result.getClass());
//            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
//                String name = propertyDescriptor.getName();
//                Object value = resultWrapper.getPropertyValue(name);
//                structureHandler.setLocalVariable(name, value);
//            }

//            String newAttributeName = "th:" + currentAttributeName.getAttributeName();
//            structureHandler.replaceAttribute(currentAttributeName, newAttributeName, template);
        }
    }

    protected AbstractSeedProcessor(TemplateMode templateMode, String dialectPrefix, String elementName, boolean prefixElementName, String attributeName, boolean prefixAttributeName, int precedence, boolean removeAttribute) {
        super(templateMode, dialectPrefix, elementName, prefixElementName, attributeName, prefixAttributeName, precedence, removeAttribute);
    }
}
