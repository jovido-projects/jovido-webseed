package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/hosts")
public class HostListingController {

    @Autowired
    private HostService hostService;

    @RequestMapping
    protected String list(Model model) {

        List<Host> hosts = hostService.getAllHosts();
        model.addAttribute("hosts", hosts);

        return "admin/host/listing";
    }
}
