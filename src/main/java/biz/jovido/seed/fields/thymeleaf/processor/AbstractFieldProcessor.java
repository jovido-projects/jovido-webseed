package biz.jovido.seed.fields.thymeleaf.processor;

import biz.jovido.seed.fields.FieldTemplateResolver;
import biz.jovido.seed.fields.thymeleaf.FieldsDialect;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring4.naming.SpringContextVariableNames;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.NoOpToken;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.beans.PropertyDescriptor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractFieldProcessor extends AbstractAttributeTagProcessor {

    private final FieldsDialect dialect;

    private static String unwrapSelectionExpressionIfRequired(String value) {
        if (value.startsWith("*{") && value.endsWith("}")) {
            return value.substring(2, value.length() - 1);
        }

        return value;
    }

//    private static String buildTemplateCacheKey(Class propertyHolderClass, String propertyName) {
//        return propertyHolderClass.getName() + '#' + propertyName;
//    }
//
//    private final Map<String, String> templateCache = new HashMap<>();
//
//    private String resolveTemplate(Class propertyHolderClass, String propertyName) {
//        String templateCacheKey = buildTemplateCacheKey(propertyHolderClass, propertyName);
//        String template = templateCache.get(templateCacheKey);
//
//        if (StringUtils.isEmpty(template)) {
//            List<FieldTemplateResolver> fieldResolvers = dialect.getFieldResolvers();
//            for (FieldTemplateResolver fieldResolver : fieldResolvers) {
//                template = fieldResolver.resolveFieldTemplate(propertyHolderClass, propertyName);
//                if (!StringUtils.isEmpty(template)) {
////                    templateCache.put(templateCacheKey, template);
//                    break;
//                }
//            }
//        }
//
//        return template;
//    }

    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag,
                             final AttributeName currentAttributeName,
                             String attributeValue,
                             IElementTagStructureHandler structureHandler) {

        IStandardExpressionParser expressionParser = StandardExpressions
                .getExpressionParser(context.getConfiguration());

        attributeValue = unwrapSelectionExpressionIfRequired(attributeValue);

        IStandardExpression expression = expressionParser.parseExpression(context, attributeValue);
        Object expressionResult = expression.execute(context);

        if (expressionResult == null || NoOpToken.class.isAssignableFrom(expressionResult.getClass())) {
            return;
        }

        IStandardExpression objectExpression = (IStandardExpression)
                context.getVariable(SpringContextVariableNames.SPRING_BOUND_OBJECT_EXPRESSION);

        if (ObjectUtils.isEmpty(objectExpression)) {
            throw new TemplateProcessingException("Missing form backing object",
                    tag.getTemplateName(), tag.getLine(), tag.getCol());
        }

        Object object = objectExpression.execute(context);
        String fullPropertyPath = expressionResult.toString();

//        Integer index = null;
//
//        Pattern indexSelectorPattern = Pattern.compile(".+(\\[([0-9]+)\\])$");
//        Matcher indexSelectorMatcher = indexSelectorPattern.matcher(fullPropertyPath);
//        if (indexSelectorMatcher.matches()) {
//            String indexSelector = indexSelectorMatcher.group(1);
//            index = NumberUtils.parseNumber(indexSelectorMatcher.group(2), Integer.class);
//            fullPropertyPath = fullPropertyPath.substring(0,
//                    fullPropertyPath.length() - indexSelector.length());
//        }

        BeanWrapper objectWrapper = new BeanWrapperImpl(object);
        objectWrapper.setAutoGrowNestedPaths(true);
        PropertyDescriptor propertyDescriptor = objectWrapper.getPropertyDescriptor(fullPropertyPath);
        String propertyName = propertyDescriptor.getName();

        String nestedPath;

        if (fullPropertyPath.length() > propertyName.length()) {
            nestedPath = fullPropertyPath.substring(0,
                    fullPropertyPath.length() - propertyName.length() - 1);
        } else {
            nestedPath = "";
        }

        Object propertyHolder = !StringUtils.isEmpty(nestedPath)
                ? objectWrapper.getPropertyValue(nestedPath)
                : object;

        Class propertyHolderClass = propertyHolder.getClass();
//        String template = resolveTemplate(propertyHolderClass, propertyName, index != null);
        FieldTemplateResolver fieldTemplateResolver = dialect.getFieldTemplateResolver();
        Assert.notNull(fieldTemplateResolver);

        String template = fieldTemplateResolver.resolveFieldTemplate(propertyHolderClass, propertyName);

        if (!StringUtils.isEmpty(template)) {
            String newAttributeName = "th:" + currentAttributeName.getAttributeName();
            structureHandler.replaceAttribute(currentAttributeName, newAttributeName, template);
            structureHandler.setLocalVariable("fullPropertyPath", fullPropertyPath);
            structureHandler.setLocalVariable("nestedPath", nestedPath);
            structureHandler.setLocalVariable("propertyName", propertyName);

            Object value = objectWrapper.getPropertyValue(fullPropertyPath);
            structureHandler.setLocalVariable("value", value);
//            structureHandler.setLocalVariable("index", index);

            Class propertyType = value != null
                    ? value.getClass()
                    : propertyDescriptor.getPropertyType();
            structureHandler.setLocalVariable("propertyType", propertyType);
            structureHandler.setLocalVariable("propertyHolderClass", propertyHolderClass);
            structureHandler.setLocalVariable("propertyDescriptor", propertyDescriptor);
        } else {
            String message = "Unable to resolve template for class " +
                    "[" + propertyHolderClass + "] and property " +
                    "[" + propertyName + "]";
            throw new TemplateProcessingException(message, tag.getTemplateName(), tag.getLine(), tag.getCol());
        }
    }

    protected AbstractFieldProcessor(FieldsDialect dialect, TemplateMode templateMode, String dialectPrefix, String elementName, boolean prefixElementName, String attributeName, boolean prefixAttributeName, int precedence, boolean removeAttribute) {
        super(templateMode, dialectPrefix, elementName, prefixElementName, attributeName, prefixAttributeName, precedence, removeAttribute);
        this.dialect = dialect;
    }
}
