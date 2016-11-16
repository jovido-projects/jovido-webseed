package biz.jovido.webseed.web.content

import biz.jovido.webseed.model.content.Fragment
import biz.jovido.webseed.service.content.FragmentService
import biz.jovido.webseed.web.content.response.FragmentResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

/**
 *
 * @author Stephan Grundner
 */
@Controller
class FragmentController {

    static class FragmentForm {

        Fragment fragment
    }

    protected final FragmentService fragmentService

    @Autowired
    FragmentController(FragmentService fragmentService) {
        this.fragmentService = fragmentService
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

        form.fragment = fragment

        model.addAttribute('form', form)

        "fragment/form"
    }
}
