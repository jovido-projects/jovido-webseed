package biz.jovido.seed.net;

import biz.jovido.seed.mvc.Breadcrumb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/host")
@SessionAttributes(types = {HostEditor.class})
public class HostEditorController {

    @Autowired
    private HostService hostService;

    @ModelAttribute("breadcrumbs")
    protected List<Breadcrumb> breadcrumbs(@ModelAttribute HostEditor editor) {
        if (editor == null || editor.getHost() == null) {
            return Collections.EMPTY_LIST;
        }
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("seed.home", "/admin"));
        breadcrumbs.add(new Breadcrumb("seed.host.listing.title", "/admin/hosts"));
        breadcrumbs.add(new Breadcrumb(editor.getHost().getName()));
        return breadcrumbs;
    }

    @ModelAttribute
    protected HostEditor editor() {
        return new HostEditor();
    }

    @RequestMapping
    protected String index(@ModelAttribute HostEditor editor) {

        return "admin/host/editor-page";
    }

    @RequestMapping(path = "edit")
    protected String edit(@ModelAttribute HostEditor editor,
                          BindingResult bindingResult,
                          @RequestParam(name = "id") Long id) {
        Host host = hostService.getHost(id);
        editor.setHost(host);

        return "redirect:";
    }

    @RequestMapping(path = "save")
    protected String save(@ModelAttribute HostEditor editor,
                          BindingResult bindingResult) {
        Host host = editor.getHost();
        host = hostService.saveHost(host);
        editor.setHost(host);

        return "redirect:";
    }
}
