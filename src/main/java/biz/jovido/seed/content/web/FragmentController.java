package biz.jovido.seed.content.web;

import biz.jovido.seed.content.domain.Fragment;
import biz.jovido.seed.content.service.FragmentService;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public abstract class FragmentController<T extends Fragment> {

    private final Class<T> fragmentClass;
    private final FragmentService fragmentService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private EntityManager entityManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/list")
    public String list(Model model) {

        String fragmentEntityName = fragmentService.getFragmentEntityName(fragmentClass);

        String queryText = "from " + fragmentEntityName + " e";
        Query query = entityManager.createQuery(queryText);
        List results = query.getResultList();

        model.addAttribute("fragments", results);

        return "/fragment/list";
    }

    @RequestMapping(path = "/create", params = {"type"})
    public String create(@RequestParam("type") String entityName, Model model) {
        Fragment fragment = fragmentService.createFragmentForEntityName(entityName);
        FragmentForm form = new FragmentForm();
        form.setFragment(fragment);

        model.addAttribute(form);

        return "/fragment/form";
    }

    @RequestMapping("/create")
    public String create(Model model) {
        Fragment fragment = fragmentService.createFragment(fragmentClass);
        FragmentForm form = new FragmentForm();
        form.setFragment(fragment);

        model.addAttribute(form);

        return "/fragment/form";
    }

    @RequestMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Fragment fragment = fragmentService.getFragment(id);
        FragmentForm form = new FragmentForm();
        form.setFragment(fragment);

        model.addAttribute(form);

        return "/fragment/form";
    }

    @Transactional
    @RequestMapping("/save")
    public String save(@Valid FragmentForm form, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            Fragment fragment = fragmentService.saveFragment(form.getFragment());
            form.setFragment(fragment);
        }

//        model.addAttribute(form);

        return "/fragment/form";
    }

    protected boolean addValue(Fragment fragment, String propertyName, Object value) {
        BeanWrapper wrapper = new BeanWrapperImpl(fragment);
        @SuppressWarnings("unchecked")
        Collection<Object> collection = (Collection<Object>)
                wrapper.getPropertyValue(propertyName);
        return collection.add(value);
    }

    @RequestMapping("/addvalue")
    public String addValue(FragmentForm form,
                           @RequestParam(name = "property") String propertyName,
                           @RequestParam(name = "type") String valueType,
                           Model model) {
        Fragment fragment = form.getFragment();

//        TODO May be not a fragment?
        Object value = fragmentService.createFragment(valueType);
        addValue(fragment, propertyName, value);

        model.addAttribute(form);

        return "/fragment/form";
    }

    protected boolean movePropertyUp(Fragment fragment, String propertyName, int index) {
        BeanWrapper wrapper = new BeanWrapperImpl(fragment);
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>)
                wrapper.getPropertyValue(propertyName);

        Object element = list.get(index);
        list.set(index, list.get(index - 1));
        list.set(index - 1, element);

        return true;
    }

    @RequestMapping("/ptyup")
    public String moveUp(FragmentForm form,
                         @RequestParam(name = "field") String propertyName,
                         @RequestParam(name = "index") int index,
                         Model model) {
        Fragment fragment = form.getFragment();
        movePropertyUp(fragment, propertyName, index);

        model.addAttribute(form);

        return "/fragment/form";
    }

    protected Object removeValue(Fragment fragment, String propertyName, int index) {
        BeanWrapper wrapper = new BeanWrapperImpl(fragment);
        @SuppressWarnings("unchecked")
        List<Object> collection = (List<Object>)
                wrapper.getPropertyValue(propertyName);
        return collection.remove(index);
    }

    @RequestMapping("/remvalue")
    public String removeValue(FragmentForm form,
                              @RequestParam(name = "field") String propertyName,
                              @RequestParam(name = "index") int index,
                              Model model) {
        Fragment fragment = form.getFragment();
        Object removed = removeValue(fragment, propertyName, index);

        model.addAttribute(form);

        return "/fragment/form";
    }

    protected FragmentController(Class<T> fragmentClass, FragmentService fragmentService) {
        this.fragmentClass = fragmentClass;
        this.fragmentService = fragmentService;
    }

    @SuppressWarnings("unchecked")
    protected FragmentController(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
        fragmentClass = fragmentService.getFragmentClass((Class<? extends FragmentController<T>>) getClass());
    }
}
