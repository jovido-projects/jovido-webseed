package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Fragment;
import biz.jovido.seed.content.service.FragmentHandler;
import biz.jovido.seed.content.service.NodeService;
import biz.jovido.seed.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Stephan Grundner
 */
public abstract class FragmentController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private ApplicationContext applicationContext;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);

        // true passed to CustomDateEditor constructor means convert empty String to null
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/create")
    public String create(@RequestParam("type") String clazzName, Model model) {

        Fragment fragment = PropertyUtils.instantiateClass(clazzName, Fragment.class,
                applicationContext.getClassLoader());

        FragmentForm form = new FragmentForm();
        form.setFragment(fragment);

        model.addAttribute(form);

        return "/fragment/form";
    }

    @RequestMapping("/edit")
    public String create(@RequestParam("id") Long id, Model model) {

        Fragment fragment = nodeService.getFragment(id);

        FragmentForm form = new FragmentForm();
        form.setFragment(fragment);

        model.addAttribute(form);

        return "/fragment/form";
    }

    @RequestMapping("/save")
    public String save(@Valid FragmentForm form, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {
            Fragment fragment = nodeService.saveFragment(form.getFragment());
            form.setFragment(fragment);
        }

        model.addAttribute(form);

        return "/fragment/form";
    }

    @RequestMapping("/addvalue")
    public String addValue(FragmentForm form,
                           @RequestParam(name = "field") String fieldName,
                           Model model) {

        Fragment fragment = form.getFragment();
        FragmentHandler fragmentHandler = nodeService.getFragmentHandler(fragment.getClass());
        fragmentHandler.addValue(fragment, fieldName, null);

        model.addAttribute(form);

        return "/fragment/form";
    }

    @RequestMapping("/ptyup")
    public String moveUp(FragmentForm form,
                         @RequestParam(name = "field") String fieldName,
                         @RequestParam(name = "index") int index,
                         Model model) {

        Fragment fragment = form.getFragment();
        FragmentHandler fragmentHandler = nodeService.getFragmentHandler(fragment.getClass());
        fragmentHandler.movePropertyUp(fragment, fieldName, index);

        model.addAttribute(form);

        return "/fragment/form";
    }

    @RequestMapping("/remvalue")
    public String removeValue(FragmentForm form,
                              @RequestParam(name = "field") String fieldName,
                              @RequestParam(name = "index") int index,
                              Model model) {

        Fragment fragment = form.getFragment();
        FragmentHandler fragmentHandler = nodeService.getFragmentHandler(fragment.getClass());
        Object removed = fragmentHandler.removeValue(fragment, fieldName, index);

        model.addAttribute(form);

        return "/fragment/form";
    }
}
