package biz.jovido.seed.content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = {"/admin/"})
public class HomeController {

//    @ModelAttribute("breadcrumbs")
//    protected List<Breadcrumb> breadcrumbs() {
//        List<Breadcrumb> breadcrumbs = new ArrayList<>();
//        breadcrumbs.add(new Breadcrumb("seed.home"));
//        return breadcrumbs;
//    }

    @RequestMapping
    protected String index() {

        return "admin/home";
    }
}
