package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/hosts")
public class HostListingController {

    @Autowired
    private HostService hostService;

    @ModelAttribute("breadcrumbs")
    protected List<Breadcrumb> breadcrumbs() {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home", "/"));
        breadcrumbs.add(new Breadcrumb("Administration", "/admin/"));
        breadcrumbs.add(new Breadcrumb("Configuration"));
        return breadcrumbs;
    }

    @RequestMapping
    protected String list(Model model) {

        List<Host> hosts = hostService.getAllHosts();
        model.addAttribute("hosts", hosts);

        return "admin/host/listing";
    }
}
