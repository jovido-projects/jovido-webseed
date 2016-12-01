package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.fragment.Payload;
import biz.jovido.seed.content.model.node.fragment.Property;
import biz.jovido.seed.content.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Stephan Grundner
 */
@RequestMapping
@Controller
public class NodeController {

    @ModelAttribute("availableLocales")
    List<Locale> availableLocales(HttpServletRequest request) {
        return Stream.of("de", "en", "it")
                .map(Locale::forLanguageTag)
                .collect(Collectors.toList());
    }

    @RequestMapping("/admin/")
    public String index() {

        return "admin/index";
    }

    @RequestMapping("/admin/node/create")
    public String create(@RequestParam(name = "type") String typeName, Model model) {

        NodeForm form = new NodeForm();
        form.setLocale(Locale.GERMAN);

        Node node = nodeService.createNode(typeName);
        Fragment fragment = nodeService.createFragment(node, form.getLocale());

        nodeService.applyDefaults(fragment);

        nodeService.setValue("title", 0, fragment, "Willkommen");
        nodeService.setValue("title", 1, fragment, "Griaß Di");

        form.setNode(node);

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/edit")
    public String edit(@RequestParam(name = "node") Long nodeId, Model model) {

        Locale locale = Locale.GERMAN;
        NodeForm form = new NodeForm();
        Node node = nodeService.getNode(nodeId);

        form.setLocale(locale);
        form.setNode(node);

//        def fragment = form.fragment

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/remvalue")
    public String removeValue(NodeForm form, BindingResult bindingResult, @RequestParam(name = "field") String fieldName, @RequestParam(name = "index") int index, Model model) {

        Fragment fragment = form.getFragment();
        Property property = fragment.getProperty(fieldName);
        int before = property.size();
        property.removePayload(index);
        int after = property.size();

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/valueup")
    public String moveValueUp(NodeForm form, BindingResult bindingResult, @RequestParam(name = "field") String fieldName, @RequestParam(name = "index") int index, Model model) {

        Fragment fragment = form.getFragment();
        Property property = fragment.getProperty(fieldName);

        Payload previousPayload = property.getPayload((int) index - 1);
        Payload payload = property.getPayload(index);

        property.setPayload(index, previousPayload);
        property.setPayload((int) index - 1, payload);

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/valuedown")
    public String moveValueDown(NodeForm form, BindingResult bindingResult, @RequestParam(name = "field") String fieldName, @RequestParam(name = "index") int index, Model model) {

        Fragment fragment = form.getFragment();
        Property property = fragment.getProperty(fieldName);

        Payload nextPayload = property.getPayload(index + 1);
        Payload payload = property.getPayload(index);

        property.setPayload(index, nextPayload);
        property.setPayload(index + 1, payload);

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/addvalue")
    public String addValue(NodeForm form, BindingResult bindingResult, @RequestParam(name = "field") String fieldName, Model model) {

        Fragment fragment = form.getFragment();
        Property property = fragment.getProperty(fieldName);
        property.setSize(property.size() + 1);
        nodeService.setValue(fieldName, property.size() - 1, fragment, null);

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/save")
    public String save(NodeForm form, BindingResult bindingResult, Model model) {

        nodeService.saveNode(form.getNode());

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    public NodeService getNodeService() {
        return nodeService;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Autowired
    private NodeService nodeService;
}
