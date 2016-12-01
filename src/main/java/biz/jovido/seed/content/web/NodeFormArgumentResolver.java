package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.fragment.Property;
import biz.jovido.seed.content.service.NodeService;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
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

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(NodeForm.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        final HttpServletRequest request = DefaultGroovyMethods.asType(webRequest.getNativeRequest(), HttpServletRequest.class);

        NodeForm form = new NodeForm();

        String name = ModelFactory.getNameForParameter(parameter);
        WebDataBinder binder = binderFactory.createBinder(webRequest, form, name);
//        binder.setAutoGrowNestedPaths(false)
        binder.setDisallowedFields("fragment.properties");

        if (!ObjectUtils.isEmpty(binder.getTarget())) {
            if (!mavContainer.isBindingDisabled(name)) {
                ((ServletRequestDataBinder) binder).bind(request);
            }
        }

        Locale locale = form.getLocale();
        Node node = form.getNode();

        final Fragment fragment = nodeService.createFragment(node, locale);

        String[] fieldNames = ServletRequestUtils.getStringParameters(request, "fragment.properties");
        Arrays.stream(fieldNames).forEach(fieldName -> {
            String[] values = ServletRequestUtils.getStringParameters(request, "fragment.properties[" + fieldName + "]");
            Property property = fragment.getProperty(fieldName);
            property.setSize(values.length);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                nodeService.setValue(fieldName, i, fragment, value);
            }
        });

        BindingResult bindingResult = binder.getBindingResult();
        Map<String, Object> bindingResultModel = bindingResult.getModel();
        mavContainer.removeAttributes(bindingResultModel);
        mavContainer.addAllAttributes(bindingResultModel);

        return form;
    }
}
