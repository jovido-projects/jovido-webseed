package biz.jovido.webseed.web.content

import biz.jovido.webseed.model.content.Field
import biz.jovido.webseed.model.content.Fragment
import biz.jovido.webseed.service.content.FragmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.core.convert.converter.ConverterRegistry
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor
import org.springframework.web.method.annotation.ModelFactory
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

import javax.servlet.http.HttpServletRequest

/**
 *
 * @author Stephan Grundner
 */
@Component
class FragmentFormArgumentResolver implements HandlerMethodArgumentResolver {

    protected final FragmentService fragmentService
    protected final FragmentFormValidator fragmentFormValidator

    @Autowired
    FragmentFormArgumentResolver(FragmentService fragmentService, FragmentFormValidator fragmentFormValidator) {
        this.fragmentService = fragmentService
        this.fragmentFormValidator = fragmentFormValidator
    }

    @Override
    boolean supportsParameter(MethodParameter parameter) {
        parameter.parameterType == FragmentForm
    }

    /**
     * @see {@link ModelAttributeMethodProcessor}
     */
    @Override
    Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        def request = webRequest.nativeRequest as HttpServletRequest
        def fragmentTypeId = Long.parseLong(request.getParameter('fragment.type'))

        Fragment fragment

        fragment = fragmentService.createFragment(fragmentTypeId)
        fragment.type.fields.each { String fieldName, Field field ->
            def values = request.getParameterValues("fragment.attributes[$fieldName].value")
            values.eachWithIndex { String value, int i ->
                fragmentService.setValue(fragment, fieldName, i, value)
            }
        }

        def form = new FragmentForm()
        form.fragment = fragment

        String name = ModelFactory.getNameForParameter(parameter)
        def binder = binderFactory.createBinder(webRequest, form, name)
        binder.setAutoGrowNestedPaths(false)
        binder.setDisallowedFields('fragment.attributes')
        binder.setDisallowedFields('fragment.attributes*')

        def converterRegistry = (ConverterRegistry) binder.conversionService
        converterRegistry.addConverter(new FieldNameToFieldConverter(binder))

//        binder.addValidators(fragmentFormValidator)

        binder.validate()
        def bindingResult = binder.bindingResult
        def bindingResultModel = bindingResult.model
        mavContainer.removeAttributes(bindingResultModel)
        mavContainer.addAllAttributes(bindingResultModel)

        form
    }
}
