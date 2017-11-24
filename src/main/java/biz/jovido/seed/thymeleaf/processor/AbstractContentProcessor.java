package biz.jovido.seed.thymeleaf.processor;

import biz.jovido.seed.content.Item;
import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.frontend.ItemValues;
import biz.jovido.seed.thymeleaf.TemplateNameResolver;
import org.springframework.ui.ExtendedModelMap;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractContentProcessor extends AbstractAttributeTagProcessor {

    private ItemService itemService;
    private List<TemplateNameResolver> templateNameResolvers;

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public List<TemplateNameResolver> getTemplateNameResolvers() {
        if (templateNameResolvers == null) {
            return Collections.EMPTY_LIST;
        }

        return templateNameResolvers;
    }

    public void setTemplateNameResolvers(List<TemplateNameResolver> templateNameResolvers) {
        this.templateNameResolvers = templateNameResolvers;
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

        if (result instanceof ItemValues) {
            Item item = ((ItemValues) result).getItem();
            String templateName = getTemplateNameResolvers().stream()
                    .map(it -> it.resolveTemplateName(item))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);

            if (StringUtils.isEmpty(templateName)) {
                templateName = item.getStructureName();
            }

//            TODO Create adapter for structureHandler
            ExtendedModelMap model = new ExtendedModelMap();
            itemService.itemToModel(item, model);
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                structureHandler.setLocalVariable(entry.getKey(), entry.getValue());
            }

            String newAttributeName = "th:" + currentAttributeName.getAttributeName();
            structureHandler.replaceAttribute(currentAttributeName, newAttributeName, templateName);
        }
    }

    protected AbstractContentProcessor(TemplateMode templateMode, String dialectPrefix, String elementName, boolean prefixElementName, String attributeName, boolean prefixAttributeName, int precedence, boolean removeAttribute) {
        super(templateMode, dialectPrefix, elementName, prefixElementName, attributeName, prefixAttributeName, precedence, removeAttribute);
    }
}
