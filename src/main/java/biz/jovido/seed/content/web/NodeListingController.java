package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.Structure;
import biz.jovido.seed.content.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Stephan Grundner
 */
@RequestMapping
@Controller
public class NodeListingController {

    @Autowired
    private NodeService nodeService;

    @RequestMapping(path = "/admin/nodes", params = {"!structure"})
    public String getAll(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int offset,
                         @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Node> page = nodeService.getAllNodes(new PageRequest(offset, size));

        model.addAttribute("page", page);

        int totalPages = page.getTotalPages();
        int pages = Math.min(totalPages, 5);

        model.addAttribute("pages", pages);

        return "/admin/node/listing";
    }

    @RequestMapping(path = "/admin/nodes", params = {"structure"})
    public String getAllByStructure(@RequestParam("structure") String structureName,
                       Model model) {
        Structure structure = nodeService.getStructure(structureName);
        Page<Node> page = nodeService.getAllNodes(structure, new PageRequest(0, 100));

        model.addAttribute("page", page);

        return "/admin/node/listing";
    }
}
