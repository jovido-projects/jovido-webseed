package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.fragment.Property;
import biz.jovido.seed.content.service.NodeService;
import biz.jovido.seed.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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

    @RequestMapping("/admin/node/new")
    public String create(@RequestParam(name = "structure") String structureName, Model model) {

        NodeForm form = new NodeForm();
        form.setLocale(Locale.GERMAN);

        Node node = nodeService.createNode(structureName);
        Fragment fragment = nodeService.createFragment(node, form.getLocale());

        nodeService.applyDefaults(fragment);

        form.setNode(node);

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/assignnew")
    public String createAndAssign(NodeForm referringForm,
            @RequestParam(name = "structure") String structureName,
                                  @RequestParam(name = "to-property") Long referringPropertyId,
                                  @RequestParam(name = "to-index") int referringValueIndex,
                                  Model model) {

        NodeForm form = new NodeForm();

        form.getReferringValues().addAll(referringForm.getReferringValues());

        form.setLocale(Locale.GERMAN);

        NodeForm.ReferringValue referringValue = new NodeForm.ReferringValue();
        referringValue.setPropertyId(referringPropertyId);
        referringValue.setValueIndex(referringValueIndex);
        form.addReferringValue(referringValue);

        Node node = nodeService.createNode(structureName);
        Fragment fragment = nodeService.createFragment(node, form.getLocale());

        nodeService.applyDefaults(fragment);

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

    @RequestMapping("/admin/node/valueup")
    public String moveValueUp(NodeForm form, BindingResult bindingResult, @RequestParam(name = "field") String fieldName, @RequestParam(name = "index") int index, Model model) {

        Fragment fragment = form.getFragment();
        Property property = fragment.getProperty(fieldName);

        Collections.swap(property.getValues(), index, index - 1);

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/valuedown")
    public String moveValueDown(NodeForm form, BindingResult bindingResult, @RequestParam(name = "field") String fieldName, @RequestParam(name = "index") int index, Model model) {

        Fragment fragment = form.getFragment();
        Property property = fragment.getProperty(fieldName);

        Collections.swap(property.getValues(), index, index + 1);

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/addvalue")
    public String addValue(NodeForm form, BindingResult bindingResult, @RequestParam(name = "field") String fieldName, Model model) {

        Fragment fragment = form.getFragment();
        Property property = fragment.getProperty(fieldName);
        CollectionUtils.increase(property.getValues(), 1);

        model.addAttribute("form", form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/remvalue")
    public String removeValue(NodeForm form, BindingResult bindingResult, @RequestParam(name = "field") String fieldName, @RequestParam(name = "index") int index, Model model) {

        Fragment fragment = form.getFragment();
        Property property = fragment.getProperty(fieldName);
        property.getValues().remove(index);

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
