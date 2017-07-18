package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/item")
public class ContentController {

    @Autowired
    private ItemService itemService;

    @RequestMapping
    protected String byId(@RequestParam(name = "id") Long itemId, Model model) {
        Item item = itemService.findItemById(itemId);
        Fragment fragment = item.getActiveFragment();
        Structure structure = itemService.getStructure(fragment);
        model.addAttribute("item", item);
        model.addAttribute("fragment", fragment);
        return structure.getName();
    }
}
