package biz.jovido.webseed.web.content

import biz.jovido.webseed.service.content.FragmentService
import biz.jovido.webseed.validation.BindingMessages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.support.ConfigurableConversionService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest

/**
 *
 * @author Stephan Grundner
 */
@Controller
class FragmentController {

    static abstract class FormProcessingHandler {

        void onSuccess() {}
    }

    protected final FragmentService fragmentService

    @Autowired
    FragmentController(FragmentService fragmentService) {
        this.fragmentService = fragmentService
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {
        def cs = (ConfigurableConversionService) binder.conversionService
        cs.addConverter(new FieldNameToFieldConverter(binder))
    }

    @RequestMapping(path = '/fragment', produces = ['application/json'])
    @ResponseBody
    FragmentResponse byId(@RequestParam(name = 'id') String id,
                          @RequestParam(name = 'revision') String revisionId) {

        def fragment = fragmentService.getFragment(id, revisionId)
        new FragmentResponse(fragment)
    }

    @RequestMapping(path = '/create')
    String create(@RequestParam(name = 'type', required = true) String fragmentTypeName,
                  Model model) {

        def form = new FragmentForm()
        def fragment = fragmentService.createFragment(fragmentTypeName)

        fragment.type.fields.values().each { field ->
            def constraint = field.constraint
            for (int i = 0; i < constraint.minValues; i++) {
                fragmentService.setValue(fragment, field.name, i, Locale.GERMAN, null)
            }
        }

        def fieldGroups = fragment.type.fieldGroups.values()
        form.fieldGroupName = fieldGroups.empty ? '' : fieldGroups.first().name
        form.fragment = fragment

        model.addAttribute('fragmentForm', form)

        "fragment/form"
    }

    protected ModelAndView processForm(FragmentForm form, BindingResult bindingResult, FormProcessingHandler handler) {
        def result = new ModelAndView()
        result.addObject('form', form)

        HttpServletRequest request = null
        def requestAttributes = RequestContextHolder.requestAttributes
        if (requestAttributes instanceof ServletRequestAttributes) {
            request = requestAttributes.request
        }

        result.viewName = "fragment/form"

        if (bindingResult.hasErrors()) {
//            TODO On errors...
        }

        handler?.onSuccess()

        result
    }

    @RequestMapping('/save')
//    ModelAndView save(FragmentForm form, BindingResult bindingResult) {
    ModelAndView save(FragmentForm form, BindingMessages bindingMessages) {
        processForm(form, bindingMessages, new FormProcessingHandler() {
            @Override
            void onSuccess() {
                println "Juhu :)"
            }
        })
    }
}
