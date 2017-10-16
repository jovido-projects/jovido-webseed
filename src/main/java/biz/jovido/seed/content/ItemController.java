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
@RequestMapping(path = "/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping
    protected String itemById(@RequestParam(name = "id") Long id, Model model) {
        Item item = itemService.getItem(id);
        model.addAttribute("item", item);

        Structure structure = item.getStructure();
        for (Attribute attribute : structure.getAttributes()) {
            String attributeName = attribute.getName();
            Sequence<?> sequence = item.getSequence(attributeName);
            model.addAttribute(attributeName, sequence);
        }
        Type type = structure.getType();

        String viewName = String.format("%s-%d", type.getName(), structure.getRevision());

        return viewName;
    }
}
