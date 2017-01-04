package biz.jovido.seed.content.web;

import biz.jovido.seed.content.domain.Page;
import biz.jovido.seed.content.service.FragmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Stephan Grundner
 */
@Controller
@FragmentType(Page.class)
@RequestMapping("/admin/node")
public class PageController extends FragmentController {

    public PageController(FragmentService fragmentService) {
        super(fragmentService);
    }
}
