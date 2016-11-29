package biz.jovido.seed.web.content

import biz.jovido.seed.service.content.NodeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.ServletRequestDataBinder
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
class NodeFormArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired NodeService nodeService

    @Override
    boolean supportsParameter(MethodParameter parameter) {
        parameter.parameterType == NodeForm
    }

    /**
     * @see {@link ModelAttributeMethodProcessor}
     */
    @Override
    Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        def request = webRequest.nativeRequest as HttpServletRequest

//        def structureId = ServletRequestUtils.getLongParameter(request, 'node.type.id')

        def form = new NodeForm()

        String name = ModelFactory.getNameForParameter(parameter)
        def binder = binderFactory.createBinder(webRequest, form, name)
//        binder.setAutoGrowNestedPaths(false)
        binder.setDisallowedFields('node.attributes')
        binder.setDisallowedFields('node.attributes*')
        binder.setDisallowedFields('node.contentBundles*')

        if (!ObjectUtils.isEmpty(binder.target)) {
            if (!mavContainer.isBindingDisabled(name)) {
                ((ServletRequestDataBinder) binder).bind(request)
            }
        }

        def bindingResult = binder.bindingResult
        def bindingResultModel = bindingResult.model
        mavContainer.removeAttributes(bindingResultModel)
        mavContainer.addAllAttributes(bindingResultModel)

        form
    }
}
