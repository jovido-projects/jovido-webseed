package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/item")
@SessionAttributes(types = {ItemEditor.class})
public class ItemEditorController {

    @Autowired
    private ItemService itemService;

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {}

    @ModelAttribute
    protected ItemEditor editor() {
        return new ItemEditor(itemService);
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemEditor editor,
                           BindingResult bindingResult) {

        return "admin/item/editor";
    }

    @RequestMapping(path = "create")
    protected String create(@ModelAttribute ItemEditor editor,
                            @RequestParam(name = "structure") String structureName,
                            BindingResult bindingResult) {

        Item item = itemService.createItem(structureName, Locale.GERMAN);
        editor.setItem(item);

        return "redirect:";
    }

    @RequestMapping(path = "save")
    protected String save(@ModelAttribute ItemEditor editor,
                          BindingResult bindingResult) {

        return "redirect:";
    }

    @RequestMapping(path = "add-relation")
    protected String addRelation(@ModelAttribute ItemEditor editor,
                                 @RequestParam(name = "nested-path") String nestedPath,
                                 @RequestParam(name = "attribute") String attributeName,
                                 @RequestParam(name = "structure") String structureName,
                                 BindingResult bindingResult) {


        return "redirect:";
    }
}
