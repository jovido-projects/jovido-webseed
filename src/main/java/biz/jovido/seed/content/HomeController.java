package biz.jovido.seed.content;

import biz.jovido.seed.mvc.Breadcrumb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = {"/admin/"})
public class HomeController {

    @ModelAttribute("breadcrumbs")
    protected List<Breadcrumb> breadcrumbs() {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("seed.home"));
        return breadcrumbs;
    }

    @RequestMapping
    protected String index() {

        return "admin/home";
    }
}
