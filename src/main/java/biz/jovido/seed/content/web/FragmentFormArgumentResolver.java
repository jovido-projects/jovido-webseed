package biz.jovido.seed.content.web;

import biz.jovido.seed.content.converter.StringToFragmentConverterFactory;
import biz.jovido.seed.content.service.FragmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.util.ClassUtils;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class FragmentFormArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger LOG = LoggerFactory.getLogger(FragmentFormArgumentResolver.class);

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

        List<String> classNameParameterNames = request.getParameterMap()
                .keySet().stream()
                .sorted((x, y) -> Integer.compare(x.length(), y.length()))
                .filter(name -> name.endsWith(".class.name"))
                .collect(Collectors.toList());

        BeanWrapper formWrapper = new BeanWrapperImpl(form);
        for (String parameterName : classNameParameterNames) {
            String propertyName = parameterName.replace(".class.name", "");
            String className = ServletRequestUtils.getStringParameter(request, parameterName);
            ClassLoader classLoader = fragmentService.getApplicationContext().getClassLoader();
            Class<?> clazz = ClassUtils.resolveClassName(className, classLoader);
            Object value = BeanUtils.instantiate(clazz);
            formWrapper.setPropertyValue(propertyName, value);
        }

        String name = ModelFactory.getNameForParameter(parameter);
        WebDataBinder binder = binderFactory.createBinder(webRequest, form, name);

        ConversionService conversionService = binder.getConversionService();
        if (conversionService instanceof ConfigurableConversionService) {
            StringToFragmentConverterFactory stringToFragmentConverterFactory =
                    fragmentService.getStringToFragmentConverterFactory();
            ((ConfigurableConversionService) conversionService)
                    .addConverterFactory(stringToFragmentConverterFactory);
        } else {
            throw new RuntimeException("Unable to register additional converters");
        }

        if (!ObjectUtils.isEmpty(binder.getTarget())) {
            if (!mavContainer.isBindingDisabled(name)) {
                ((ServletRequestDataBinder) binder).bind(request);
            }
        }

        if (parameter.hasParameterAnnotation(Valid.class)) {
            binder.validate();
        }

        BindingResult bindingResult = binder.getBindingResult();
        Map<String, Object> model = bindingResult.getModel();
        mavContainer.removeAttributes(model);
        mavContainer.addAllAttributes(model);

        return form;
    }
}
