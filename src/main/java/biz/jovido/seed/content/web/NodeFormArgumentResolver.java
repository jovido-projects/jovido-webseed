package biz.jovido.seed.content.web;

import biz.jovido.seed.content.converter.StringToNodeConverter;
import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.Structure;
import biz.jovido.seed.content.model.node.fragment.Property;
import biz.jovido.seed.content.service.NodeService;
import biz.jovido.seed.util.CollectionUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Component
public class NodeFormArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private StringToNodeConverter stringToNodeConverter;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(NodeForm.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        final HttpServletRequest request = DefaultGroovyMethods.asType(webRequest.getNativeRequest(), HttpServletRequest.class);

        String structureName = ServletRequestUtils.getStringParameter(request, "node.structure.name");
        Structure structure = nodeService.getStructure(structureName);
        Node node = nodeService.createNode(structure);
        NodeForm form = new NodeForm();
        form.setNode(node);

        String name = ModelFactory.getNameForParameter(parameter);
        WebDataBinder binder = binderFactory.createBinder(webRequest, form, name);
//        binder.setAutoGrowNestedPaths(false)
        binder.setDisallowedFields("fragment.properties");

        ConfigurableConversionService conversionService =
                (ConfigurableConversionService) binder.getConversionService();

        conversionService.addConverter(stringToNodeConverter);

        if (!ObjectUtils.isEmpty(binder.getTarget())) {
            if (!mavContainer.isBindingDisabled(name)) {
                ((ServletRequestDataBinder) binder).bind(request);
            }
        }

        Locale locale = form.getLocale();
        final Fragment fragment = nodeService.createFragment(node, locale);

        String[] fieldNames = ServletRequestUtils
                .getStringParameters(request, "fragment.properties");
        Arrays.stream(fieldNames).forEach(fieldName -> {
            String[] values = ServletRequestUtils
                    .getStringParameters(request, "fragment.properties[" + fieldName + "]");
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                nodeService.setValue(fragment, fieldName, i, value);
            }
        });

        BindingResult bindingResult = binder.getBindingResult();
        Map<String, Object> bindingResultModel = bindingResult.getModel();
        mavContainer.removeAttributes(bindingResultModel);
        mavContainer.addAllAttributes(bindingResultModel);

        return form;
    }
}
