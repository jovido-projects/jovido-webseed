package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Fragment;
import biz.jovido.seed.content.service.FragmentHandler;
import biz.jovido.seed.content.service.NodeService;
import biz.jovido.seed.content.util.FragmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring4.expression.Fields;

import javax.validation.Valid;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/admin/node/create")
    public String create(@RequestParam("type") String clazzName, Model model) {

        Fragment fragment = FragmentUtils.instantiateFragment(clazzName, applicationContext.getClassLoader());

        FragmentForm form = new FragmentForm();
        form.setFragment(fragment);

//        model.addAttribute("form", form);
        model.addAttribute(form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/edit")
    public String create(@RequestParam("id") Long id, Model model) {

        Fragment fragment = nodeService.getFragment(id);

        FragmentForm form = new FragmentForm();
        form.setFragment(fragment);

//        model.addAttribute("form", form);
        model.addAttribute(form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/save")
    public String save(@Valid FragmentForm form, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {
            Fragment fragment = nodeService.saveFragment(form.getFragment());
            form.setFragment(fragment);
        }

//        model.addAttribute("form", form);
        model.addAttribute(form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/addvalue")
    public String addValue(FragmentForm form,
                           @RequestParam(name = "field") String fieldName,
                           Model model) {
//        BeanWrapper beanWrapper = new BeanWrapperImpl(form.getFragment());
//        List<Field> fields = FragmentUtils.getControls(form.getFragment().getClass());
//        Collection collection = (Collection) beanWrapper.getPropertyValue(fieldName);
//        collection.add(null);

        Fragment fragment = form.getFragment();
        FragmentHandler fragmentHandler = nodeService.getFragmentHandler(fragment.getClass());
        fragmentHandler.addValue(fragment, fieldName, null);

        model.addAttribute(form);

        return "/admin/node/form";
    }

    @RequestMapping("/admin/node/remvalue")
    public String removeValue(FragmentForm form,
                              @RequestParam(name = "field") String fieldName,
                              @RequestParam(name = "index") int index,
                              Model model) {
//        BeanWrapper beanWrapper = new BeanWrapperImpl(form.getFragment());
//        List<Field> fields = FragmentUtils.getControls(form.getFragment().getClass());
//        List list = (List) beanWrapper.getPropertyValue(fieldName);
//        list.remove(index);

        Fragment fragment = form.getFragment();
        FragmentHandler fragmentHandler = nodeService.getFragmentHandler(fragment.getClass());
        Object removed = fragmentHandler.removeValue(fragment, fieldName, index);

//        model.addAttribute("form", form);
        model.addAttribute(form);

        return "/admin/node/form";
    }
}
