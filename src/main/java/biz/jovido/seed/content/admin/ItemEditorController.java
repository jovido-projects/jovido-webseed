package biz.jovido.seed.content.admin;

import biz.jovido.seed.ErrorUtils;
import biz.jovido.seed.content.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemAdministrationValidator administrationValidator;

    @InitBinder("itemAdministration")
    protected void init(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(
                "*value.payloads*",
                "*fragment.referringPayload",
                "*currentFragment*",
                "*activeFragment*");
        dataBinder.addValidators(administrationValidator);
    }

    private String redirect(Item item) {
        if (item != null) {
            Long id = item.getId();
            if (item.getId() != null) {
                return String.format("redirect:?id=%d", item.getId());
            } else {
                Fragment fragment = item.getCurrentFragment();
                if (fragment != null) {
                    Structure structure = fragment.getStructure();
                    if (structure != null) {
                        return String.format("redirect:?new=%s", structure.getName());
                    }
                }
            }
        }

        return "redirect:/admin/items/";
    }

    private String redirect(ItemEditor editor) {
        return redirect(editor.getItem());
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemAdministration administration,
                           BindingResult bindingResult,
                           Model model) {

        ErrorUtils.addToModel(model, administration.getErrors());

        model.addAttribute("administration", administration);

        ItemEditor editor = administration.getEditor();
        model.addAttribute("editor", editor);
        model.addAttribute("item", editor.getItem());

        Fragment fragment = editor.getFragment();
        model.addAttribute("fragment", fragment);

        return "admin/item/editor";
    }

    @RequestMapping(path = "create")
    protected String create(@ModelAttribute ItemAdministration administration,
                            BindingResult bindingResult,
                            @RequestParam(name = "structure") String structureName) {

        Locale locale = LocaleContextHolder.getLocale();
        Item item = itemService.createItem(structureName, locale);

        ItemEditor editor = administration.getEditor();
        editor.setItem(item);
        editor.setFragment(item.getCurrentFragment());
        editor.setErrors(null);

//        return "redirect:";
        return redirect(item);
    }

    @Transactional
    @RequestMapping(path = "save")
    protected String save(@Valid @ModelAttribute ItemAdministration administration,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        administration.setErrors(bindingResult);

        ItemEditor editor = administration.getEditor();
        Fragment fragment = editor.getFragment();
        if (fragment.isReferred()) {
            return "forward:close";
        } else {
            Item item = editor.getItem();
            item = itemService.saveItem(item);
            editor.setItem(item);
        }

//        return "redirect:";
        return redirect(editor);
    }

    @RequestMapping(path = "edit", params = {"field", "index"})
    protected String editDependent(@ModelAttribute ItemAdministration administration,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "field") String fieldName,
                                   @RequestParam(name = "index") int valueIndex) {

        ItemEditor editor = administration.getEditor();
        Fragment fragment = editor.getFragment();
        Field field = fragment.getField(fieldName);
        FragmentPayload payload = (FragmentPayload) field.getPayloads().get(valueIndex);
        Fragment dependentFragment = payload.getValue();
        editor.setFragment(dependentFragment);

//        return "redirect:";
        return redirect(editor);
    }

    @RequestMapping(path = "edit")
    protected String edit(@ModelAttribute ItemAdministration administration,
                          BindingResult bindingResult,
                          @RequestParam(name = "item") Long itemId) {

        Item item = itemService.findItemById(itemId);
        ItemEditor editor = administration.getEditor();
        editor.setItem(item);

//        return "redirect:";
        return redirect(editor);
    }

    @RequestMapping(path = "load")
    protected String load(@ModelAttribute ItemAdministration administration,
                          BindingResult bindingResult,
                          @RequestParam(name = "item") Long itemId,
                          RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("item", itemId);
        return "forward:edit";
    }


    @RequestMapping(path = "delete")
    protected String delete(@ModelAttribute ItemAdministration administration,
                            BindingResult bindingResult) {

        ItemEditor editor = administration.getEditor();

//        return "redirect:";
        return redirect(editor);
    }

    @RequestMapping(path = "discard")
    protected String discard(@ModelAttribute ItemAdministration administration,
                             BindingResult bindingResult) {

        ItemEditor editor = administration.getEditor();

//        return "redirect:";
        return redirect(editor);
    }

    @RequestMapping(path = "activate")
    protected String activate(@ModelAttribute ItemAdministration administration,
                              BindingResult bindingResult) {

        ItemEditor editor = administration.getEditor();
        itemService.activateItem(editor.getItem());

//        return "redirect:";
        return redirect(editor);
    }


    @RequestMapping(path = "close")
    protected String close(@ModelAttribute ItemAdministration administration,
                           BindingResult bindingResult,
                           Model model) {

        ItemEditor editor = administration.getEditor();
        Fragment fragment = editor.getFragment();
        if (fragment.isReferred()) {
            FragmentPayload dependingPayload = fragment.getReferringPayload();
            fragment = dependingPayload.getField().getFragment();
            editor.setFragment(fragment);
        }

//        return "redirect:";
        return redirect(editor);
    }

    @RequestMapping(path = "append-payload")
    protected String appendPayload(@ModelAttribute ItemAdministration administration,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "field") String fieldName,
                                   @RequestParam(name = "structure") String structureName) {

        ItemEditor editor = administration.getEditor();
        Fragment fragment = editor.getFragment();
        Field field = fragment.getField(fieldName);
        FragmentPayload payload = new FragmentPayload();
        payload.setValue(itemService.createFragment(structureName));
//        payload.getValue().setEmbedded(true);
        field.appendPayload(payload);

//        return "redirect:";
        return redirect(editor);
    }

    @RequestMapping(path = "move-payload-up")
    protected String movePayloadUp(@ModelAttribute ItemAdministration administration,
                                   @RequestParam(name = "field") String fieldName,
                                   @RequestParam(name = "index") int payloadIndex,
                                   BindingResult bindingResult) {

        ItemEditor editor = administration.getEditor();
        Fragment fragment = editor.getFragment();
        Field field = fragment.getField(fieldName);
        field.rearrangePayload(payloadIndex, payloadIndex - 1);

//        return "redirect:";
        return redirect(editor);
    }

    @RequestMapping(path = "move-payload-down")
    protected String movePayloadDown(@ModelAttribute ItemAdministration administration,
                                     @RequestParam(name = "field") String fieldName,
                                     @RequestParam(name = "index") int payloadIndex,
                                     BindingResult bindingResult) {

        ItemEditor editor = administration.getEditor();
        Fragment fragment = editor.getFragment();
        Field field = fragment.getField(fieldName);
        field.rearrangePayload(payloadIndex, payloadIndex + 1);

//        return "redirect:";
        return redirect(editor);
    }

    @RequestMapping(path = "remove-payload")
    protected String removePayload(@ModelAttribute ItemAdministration administration,
                                   @RequestParam(name = "field") String fieldName,
                                   @RequestParam(name = "index") int payloadIndex,
                                   BindingResult bindingResult) {

        ItemEditor editor = administration.getEditor();
        Fragment fragment = editor.getFragment();
        Field field = fragment.getField(fieldName);
        field.removePayload(payloadIndex);
//        Payload<?> payload = field.getPayloads().get(payloadIndex);
//        boolean toBeRemoved = payload.isToBeRemoved();
//        payload.setToBeRemoved(!toBeRemoved);

//        return "redirect:";
        return redirect(editor);
    }
}
