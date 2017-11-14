package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.Item;
import biz.jovido.seed.content.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/item2/")
@SessionAttributes(types = ItemEditor2.class)
public class ItemEditor2Controller {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ItemEditor2 editor() {
        ItemEditor2 editor = new ItemEditor2(itemService);

        return editor;
    }

    @RequestMapping()
    public String index(@ModelAttribute ItemEditor2 editor,
                       BindingResult editorBinding) {

        return "admin/item/editor2-page";
    }


    @RequestMapping(path = "edit")
    public String edit(@ModelAttribute ItemEditor2 editor,
                       BindingResult editorBinding,
                       @RequestParam(name = "id") Long itemId) {

        Item item = itemService.getItem(itemId);
        editor.setItem(item);

//        editor.getFieldGroups().get("title").getFields().get(0).remove();

        return "redirect:";
    }

    @RequestMapping(path = "save")
    public String save(@ModelAttribute ItemEditor2 editor,
                       BindingResult editorBinding) {

        Item item = editor.getItem();
        item = itemService.saveItem(item);
        editor.setItem(item);

        return "redirect:edit?id=" + editor.getItem().getId();
    }

    @RequestMapping(path = "remove-field")
    public String removeField(@ModelAttribute ItemEditor2 editor,
                              BindingResult editorBinding,
                              @RequestParam(name = "field") String fieldId) {

        ItemEditor2.PayloadField field = editor.findField(fieldId);
        field.remove();

        return "admin/item/editor2-page";
    }

    @RequestMapping(path = "move-field-up")
    public String moveFieldUp(@ModelAttribute ItemEditor2 editor,
                              BindingResult editorBinding,
                              @RequestParam(name = "field") String fieldId) {

        ItemEditor2.PayloadField field = editor.findField(fieldId);
        field.moveUp();

        return "admin/item/editor2-page";
    }

    @RequestMapping(path = "add-field")
    public String addField(@ModelAttribute ItemEditor2 editor,
                              BindingResult editorBinding,
                              @RequestParam(name = "field-group") String attributeName) {

        ItemEditor2.PayloadFieldGroup fieldGroup = editor.getFieldGroup(attributeName);

        return "admin/item/editor2-page";
    }
}
