package biz.jovido.seed.thymeleaf.processor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.Conventions;
import org.springframework.util.ClassUtils;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.engine.TemplateData;
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
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractFieldProcessor extends AbstractAttributeTagProcessor {

    private static String unwrapSelectionExpressionIfRequired(String value) {
        if (value.startsWith("*{") && value.endsWith("}")) {
            return value.substring(2, value.length() - 1);
        }

        return value;
    }

    private TemplateResolution resolveTemplate(IEngineConfiguration configuration, String ownerTemplate, String template, Map<String, Object> attributes) {
        return configuration.getTemplateResolvers().stream()
                .sorted(Comparator.comparingInt(ITemplateResolver::getOrder))
                .map(it -> it.resolveTemplate(configuration, ownerTemplate, template, attributes))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag,
                             final AttributeName currentAttributeName,
                             String attributeValue,
                             IElementTagStructureHandler structureHandler) {

        IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(context.getConfiguration());

        attributeValue = unwrapSelectionExpressionIfRequired(attributeValue);

        IStandardExpression expression = expressionParser.parseExpression(context, attributeValue);
        Object result = expression.execute(context);

        if (result == null || NoOpToken.class.isAssignableFrom(result.getClass())) {
            return;
        }

        IStandardExpression boundObjectExpression = (IStandardExpression)
                context.getVariable(SpringContextVariableNames.SPRING_BOUND_OBJECT_EXPRESSION);

        if (ObjectUtils.isEmpty(boundObjectExpression)) {
            throw new TemplateProcessingException("Missing form backing object",
                    tag.getTemplateName(), tag.getLine(), tag.getCol());
        }

        Object boundObject = boundObjectExpression.execute(context);
        String fullPropertyPath = result.toString();

        Integer index = null;

        Pattern indexSelectorPattern = Pattern.compile(".+(\\[([0-9]+)\\])$");
        Matcher indexSelectorMatcher = indexSelectorPattern.matcher(fullPropertyPath);
        if (indexSelectorMatcher.matches()) {
            String indexSelector = indexSelectorMatcher.group(1);
            index = NumberUtils.parseNumber(indexSelectorMatcher.group(2), Integer.class);
            fullPropertyPath = fullPropertyPath.substring(0,
                    fullPropertyPath.length() - indexSelector.length());
        }

        BeanWrapper boundObjectWrapper = new BeanWrapperImpl(boundObject);
        boundObjectWrapper.setAutoGrowNestedPaths(true);
        PropertyDescriptor propertyDescriptor = boundObjectWrapper.getPropertyDescriptor(fullPropertyPath);
        String propertyName = propertyDescriptor.getName();

        IEngineConfiguration configuration = context.getConfiguration();

        String nestedPath;

        if (fullPropertyPath.length() > propertyName.length()) {
            nestedPath = fullPropertyPath.substring(0,
                    fullPropertyPath.length() - propertyName.length() - 1);
        } else {
            nestedPath = "";
        }


        Object propertyHolder = !StringUtils.isEmpty(nestedPath)
                ? boundObjectWrapper.getPropertyValue(nestedPath)
                : boundObject;

        Class<?> propertyType = propertyDescriptor.getPropertyType();

        List<String> templateCandidates = new ArrayList<>();
        templateCandidates.add(String.format("%s/%s-field", Conventions.getVariableName(propertyHolder), propertyName));
        templateCandidates.add(String.format("%s/%s-field", Conventions.getVariableName(propertyHolder), ClassUtils.getShortNameAsProperty(propertyType)));
        templateCandidates.add(String.format("%s-field", ClassUtils.getShortNameAsProperty(propertyType)));

        String template = null;
        for (String templateCandidate :  templateCandidates) {
            TemplateResolution templateResolution = resolveTemplate(
                    configuration,
                    context.getTemplateData().getTemplate(),
                    templateCandidate,
                    Collections.EMPTY_MAP);

            boolean exists = templateResolution.getTemplateResource().exists();
            if (exists) {
                template = templateCandidate;
                break;
            }
        }

        if (StringUtils.hasLength(template)) {
            String newAttributeName = "th:" + currentAttributeName.getAttributeName();
            structureHandler.setLocalVariable("propertyName", propertyName);
            structureHandler.setLocalVariable("fullPropertyPath", fullPropertyPath);
            structureHandler.replaceAttribute(currentAttributeName, newAttributeName, template);
        } else {
            throw new TemplateProcessingException("Unable to resolve template", tag.getTemplateName(), tag.getLine(), tag.getCol());
        }
    }

    protected AbstractFieldProcessor(TemplateMode templateMode, String dialectPrefix, String elementName, boolean prefixElementName, String attributeName, boolean prefixAttributeName, int precedence, boolean removeAttribute) {
        super(templateMode, dialectPrefix, elementName, prefixElementName, attributeName, prefixAttributeName, precedence, removeAttribute);
    }
}
