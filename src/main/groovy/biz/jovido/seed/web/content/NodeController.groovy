package biz.jovido.seed.web.content

import biz.jovido.seed.service.content.NodeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 *
 * @author Stephan Grundner
 */
@RequestMapping
@Controller
class NodeController {

    @Autowired NodeService fragmentService

    @RequestMapping("/admin/node/create")
    String create(@RequestParam(name = 'type') String typeName, Model model) {

        def form = new NodeForm()

        def node = fragmentService.createNode(typeName)
        def fragment = fragmentService.createFragment(node, Locale.GERMAN)


        form.node = node

        model.addAttribute('form', form)

        "/admin/node/form"
    }

    @RequestMapping("/admin/node/save")
    String save(NodeForm form, BindingResult bindingResult, Model model) {

        "/admin/node/form"
    }
}
