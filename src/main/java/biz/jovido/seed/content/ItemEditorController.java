package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/item/")
public class ItemEditorController extends ItemAdministrationAdvice {

    @Autowired
    private ItemService itemService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StructureService structureService;

    @RequestMapping
    protected String index(@ModelAttribute ItemAdministration administration, Model model, Session session) {

        return "admin/item/editor";
    }

    @RequestMapping("create")
    protected String create(@ModelAttribute ItemAdministration administration,
                            BindingResult bindingResult,
                            @RequestParam(name = "structure") String structureName) {
        ItemEditor editor = administration.getEditor();

        Structure structure = structureService.findStructureByName(structureName, 2);
        Locale locale = LocaleContextHolder.getLocale();
        Item item = itemService.createNewItem(structure, locale);

        editor.setItem(item);

        return "redirect:";
    }

    @RequestMapping("load")
    protected String load(@ModelAttribute ItemAdministration administration,
                          BindingResult bindingResult,
                          @RequestParam(name = "item") Long itemId) {
        ItemEditor editor = administration.getEditor();
        Item item = itemService.findItemById(itemId);
        editor.setItem(item);

        return "redirect:";
    }

    @RequestMapping("save")
    protected String save(@ModelAttribute ItemAdministration administration,
                          BindingResult bindingResult) {
        ItemEditor editor = administration.getEditor();
        Item item = editor.getItem();

        item = itemService.saveItem(item);
        entityManager.detach(item);

        editor.setItem(item);

        return "redirect:";
    }

    @RequestMapping("discard")
    protected String discard(@ModelAttribute ItemAdministration administration,
                          BindingResult bindingResult) {
        ItemEditor editor = administration.getEditor();
        Item item = editor.getItem();

        if (item != null) {
            item = itemService.findItemById(item.getId());
            editor.setItem(item);
        } else {
            throw new RuntimeException("Oops");
        }

        return "redirect:";
    }

    @RequestMapping("append-value")
    protected String appendValue(@ModelAttribute ItemAdministration administration,
                                 BindingResult bindingResult,
                                 @RequestParam(name = "property") String propertyName) {
        ItemEditor editor = administration.getEditor();
        Item item = editor.getItem();

        itemService.appendNewValue(item, propertyName);

        return "redirect:";
    }

    @RequestMapping("move-value-up")
    protected String moveValueUp(@ModelAttribute ItemAdministration administration,
                                 BindingResult bindingResult,
                                 @RequestParam(name = "property") String propertyName,
                                 @RequestParam(name = "index") int index) {
        ItemEditor editor = administration.getEditor();
        Item item = editor.getItem();

        itemService.swapValues(item, propertyName, index, index - 1);

        return "redirect:";
    }

    @RequestMapping("move-value-down")
    protected String moveValueDown(@ModelAttribute ItemAdministration administration,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "property") String propertyName,
                                   @RequestParam(name = "index") int index) {
        ItemEditor editor = administration.getEditor();
        Item item = editor.getItem();

        itemService.swapValues(item, propertyName, index, index + 1);

        return "redirect:";
    }

    @RequestMapping("remove-value")
    protected String removeValue(@ModelAttribute ItemAdministration administration,
                                 BindingResult bindingResult,
                                 @RequestParam(name = "property") String propertyName,
                                 @RequestParam(name = "index") int index) {
        ItemEditor editor = administration.getEditor();
        Item item = editor.getItem();

        itemService.removeValue(item, propertyName, index);

        return "redirect:";
    }
}
