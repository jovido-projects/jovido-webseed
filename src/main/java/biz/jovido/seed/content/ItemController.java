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

    @RequestMapping(params = {"id", "!history"})
    protected String itemById(@RequestParam(name = "id") Long id, Model model) {
        Item item = itemService.getItem(id);
        model.addAttribute("_item", item);
        model.addAttribute("label", item.getLabel());

        Structure structure = item.getStructure();
        for (Attribute attribute : structure.getAttributes()) {
            String attributeName = attribute.getName();
            Sequence<?> sequence = item.getSequence(attributeName);
            model.addAttribute(attributeName, sequence);
        }

        String viewName = String.format("%s-%d", structure.getName(), structure.getRevision());

        return viewName;
    }

    @RequestMapping(params = {"!id", "history"})
    protected String itemByHistory(@RequestParam(name = "history") Long historyId, Model model) {
        Item item = itemService.findPublished(historyId);
        model.addAttribute("_item", item);
        model.addAttribute("label", item.getLabel());

        Structure structure = item.getStructure();
        for (Attribute attribute : structure.getAttributes()) {
            String attributeName = attribute.getName();
            Sequence<?> sequence = item.getSequence(attributeName);
            model.addAttribute(attributeName, sequence);
        }

        String viewName = String.format("%s-%d", structure.getName(), structure.getRevision());

        return viewName;
    }
}
