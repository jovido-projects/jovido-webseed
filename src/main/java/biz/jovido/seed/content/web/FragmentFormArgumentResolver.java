package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Fragment;
import biz.jovido.seed.content.service.FragmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
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
import javax.validation.Valid;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class FragmentFormArgumentResolver implements HandlerMethodArgumentResolver {

    private final FragmentService fragmentService;

    public FragmentFormArgumentResolver(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return FragmentForm.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        FragmentForm form = new FragmentForm();

        String fragmentClassName = ServletRequestUtils.getStringParameter(request, "fragment.class.name");
        try {
            Class fragmentClass = Class.forName(fragmentClassName);
            form.setFragment(BeanUtils.instantiateClass(fragmentClass, Fragment.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String name = ModelFactory.getNameForParameter(parameter);
        WebDataBinder binder = binderFactory.createBinder(webRequest, form, name);
//        binder.setAutoGrowNestedPaths(false);
//        binder.setDisallowedFields("fragment");

//        Map<String, String[]> parameters = request.getParameterMap();
//        List<Field> fields = ContentUtils.getFields(form.getFragment().getClass());
//        for (Field field : fields) {
//            String path = "fragment." + field.getName();
////            if (FragmentUtils.isFragmentField(field)) {
////                binder.setDisallowedFields(path + "*");
////            } else if (FragmentUtils.isFragmentCollectionField(field)) {
////                binder.setDisallowedFields(path + "*");
////
////                List<String> parameterNames = parameters.keySet().stream()
////                        .filter(key -> key.startsWith(path))
////                        .collect(Collectors.toList());
////
////                parameterNames.toString();
//////                TODO Bind manually!
////            }
//        }

        if (!ObjectUtils.isEmpty(binder.getTarget())) {
            if (!mavContainer.isBindingDisabled(name)) {
                ((ServletRequestDataBinder) binder).bind(request);
            }
        }

        if (parameter.hasParameterAnnotation(Valid.class)) {
//            TODO Validate form!
            binder.validate();
        }

        BindingResult bindingResult = binder.getBindingResult();
        Map<String, Object> model = bindingResult.getModel();
        mavContainer.removeAttributes(model);
        mavContainer.addAllAttributes(model);

        return form;
    }
}
