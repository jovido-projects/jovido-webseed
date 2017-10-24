package biz.jovido.seed.content;

import biz.jovido.seed.content.url.Alias;
import biz.jovido.seed.content.url.AliasService;
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

    @Autowired
    private AliasService aliasService;

    @RequestMapping(params = {"id"})
    protected String itemById(@RequestParam(name = "id") Long id, Model model) {
        Item item = itemService.getItem(id);
        model.addAttribute("_item", item);
        Sequence label = itemService.getLabel(item);
        model.addAttribute("label", label);

        Structure structure = itemService.getStructure(item);
        for (Attribute attribute : structure.getAttributes()) {
            String attributeName = attribute.getName();
            Sequence<?> sequence = item.getSequence(attributeName);
            model.addAttribute(attributeName, sequence);
        }

        return structure.getName();
    }

    @RequestMapping(params = {"leaf"})
    protected String itemByLeaf(@RequestParam(name = "leaf") Long leafId, Model model) {
        Item item = itemService.findPublished(leafId);
        model.addAttribute("_item", item);
        Sequence label = itemService.getLabel(item);
        model.addAttribute("label", label);

        Structure structure = itemService.getStructure(item);
        for (Attribute attribute : structure.getAttributes()) {
            String attributeName = attribute.getName();
            Sequence<?> sequence = item.getSequence(attributeName);
            model.addAttribute(attributeName, sequence);
        }

        return structure.getName();
    }

    @RequestMapping(params = {"alias"})
    protected String itemByAlias(@RequestParam(name = "alias") Long aliasId, Model model) {
        Alias alias = aliasService.getAlias(aliasId);

        return itemByLeaf(alias.getHistory().getId(), model);
    }
}
