package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/item")
@SessionAttributes(types = {ItemAdministration.class})
public class ItemEditorController {

    private static class Foobar implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {
            return ItemEditor.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            errors.rejectValue("fragment", "fuck.up", "Fuckup!");
        }
    }

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ItemEditor editor(@ModelAttribute ItemAdministration administration) {
        return administration.getEditor();
    }

    @InitBinder
    protected void init(WebDataBinder dataBinder) {
//        dataBinder.setAllowedFields("fragment");
        dataBinder.addValidators(new Foobar());
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemAdministration administration,
                           @Valid @ModelAttribute ItemEditor editor,
                           BindingResult bindingResult2,
                           Model model) {

        model.addAttribute("administration", administration);

        editor = administration.getEditor();
        model.addAttribute("editor", editor);
        model.addAttribute("item", editor.getItem());

        Fragment fragment = editor.getFragment();
        model.addAttribute("fragment", fragment);

        return "admin/item/editor";
    }

    @RequestMapping(path = "create")
    protected String create(@ModelAttribute ItemEditor editor,
                            BindingResult bindingResult,
                            @RequestParam(name = "structure") String structureName) {

//        editor = administration.getEditor();
//        if (editor == null) {
//            editor = new ItemEditor();
//            administration.setEditor(editor);
//        }

        Locale locale = LocaleContextHolder.getLocale();
        Item item = itemService.createItem(structureName, locale);
        editor.setItem(item);

        return "redirect:";
    }

    @Transactional
    @RequestMapping(path = "save")
    protected String save(@ModelAttribute ItemEditor editor,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        bindingResult.reject("test", "Haa haa");
        bindingResult.rejectValue("fragment", "Arghhh");
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemEditor", bindingResult);

        Fragment fragment = editor.getFragment();
        if (!fragment.isDependent()) {
            Fragment saved = itemService.saveFragment(editor.getFragment());
            editor.setFragment(saved);
        } else {
//            return "forward:close";
        }

        return "redirect:";
    }

    @RequestMapping(path = "edit")
    protected String editDependent(@ModelAttribute ItemAdministration administration,
                                   @ModelAttribute ItemEditor editor,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "field") String fieldName,
                                   @RequestParam(name = "index") int valueIndex) {

        Fragment fragment = editor.getFragment();
        Field field = fragment.getField(fieldName);
        FragmentPayload payload = (FragmentPayload) field.getPayloads().get(valueIndex);
        Fragment dependentFragment = payload.getValue();
        editor.setFragment(dependentFragment);

        return "redirect:";
    }

    @RequestMapping(path = "load")
    protected String load(@ModelAttribute ItemEditor editor,
                          BindingResult bindingResult,
                          @RequestParam(name = "fragment") Long fragmentId) {

        Fragment fragment = itemService.loadFragmentById(fragmentId);
        editor.setFragment(fragment);

        return "redirect:";
    }

    @RequestMapping(path = "delete")
    protected String delete(@ModelAttribute ItemEditor editor,
                            BindingResult bindingResult) {

        return "redirect:";
    }

    @RequestMapping(path = "discard")
    protected String discard(@ModelAttribute ItemEditor editor,
                             BindingResult bindingResult) {

        return "redirect:";
    }

    @RequestMapping(path = "close")
    protected String close(@ModelAttribute ItemEditor editor,
                           BindingResult bindingResult,
                           Model model) {

//        ItemEditor.Origin origin = editor.getChain().removeLast();
//        editor.setFragment(origin.getFragment());
//        ItemEditor.Parent parent = editor.getParent();
//        if (parent != null) {
//            model.addAttribute(parent.getEditor());
//        }

        Fragment fragment = editor.getFragment();
        if (fragment.isDependent()) {
            FragmentPayload dependingPayload = fragment.getDependingPayload();
            fragment = dependingPayload.getField().getFragment();
            editor.setFragment(fragment);
        }

        return "redirect:";
    }

    @RequestMapping(path = "append-payload")
    protected String appendPayload(@ModelAttribute ItemEditor editor,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "field") String fieldName,
                                   @RequestParam(name = "structure") String structureName) {

        Fragment fragment = editor.getFragment();
        Field<Fragment> field = fragment.getField(fieldName);
        FragmentPayload payload = new FragmentPayload();
        payload.setValue(itemService.createFragment(structureName));
//        payload.getValue().setEmbedded(true);
        field.appendPayload(payload);

        return "redirect:";
    }

}
