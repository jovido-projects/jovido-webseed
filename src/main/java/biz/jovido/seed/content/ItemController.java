package biz.jovido.seed.content;

import biz.jovido.seed.Alias;
import biz.jovido.seed.AliasService;
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
public class ItemController {

    @Autowired
    private AliasService aliasService;

    @Autowired
    private ViewNameResolver viewNameResolver;

    @RequestMapping(params = {"alias"})
    protected String byAlias(@RequestParam(name = "alias") Long aliasId, Model model) {
        Alias alias = aliasService.findAliasById(aliasId);
        Bundle bundle = alias.getBundle();
        Structure structure = bundle.getStructure();

        Item item = bundle.getPublished();
        model.addAttribute(item);

        for (Attribute attribute : structure.getAttributes()) {
            Property property = item.getProperty(attribute.getName());
            model.addAttribute(attribute.getName(), property);
        }

        return viewNameResolver.resolveViewName(item);
    }
}
