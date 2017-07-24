package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/item/")
@SessionAttributes(types = ItemEditor.class)
public class ItemEditorController {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ItemEditor editor() {
        return new ItemEditor();
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemEditor editor, BindingResult bindingResult) {

        return "admin/item/editor";
    }

    @RequestMapping(path = "create")
    protected RedirectView create(@ModelAttribute ItemEditor editor,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "structure") String structureName) {

        Item item = itemService.createItem(structureName);
        editor.setItem(item);

        return new RedirectView("");
    }

    @RequestMapping(path = "save")
    protected RedirectView save(@ModelAttribute ItemEditor editor,
                                BindingResult bindingResult) {

        return new RedirectView("");
    }
}
