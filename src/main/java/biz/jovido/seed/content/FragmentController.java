package biz.jovido.seed.content;

import biz.jovido.seed.DomainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stephan Grundner
 */
@Controller
public class FragmentController {

    private final FragmentService fragmentService;
    private final DomainService domainService;

//    @RequestMapping(path = "/{locale:[a-z]+}/{alias:.*}")
//    String index(@PathVariable("locale") Locale locale,
//                 @PathVariable("alias") String path,
//                 HttpServletRequest request) {
//
//        String serverName = request.getServerName();
//        Domain domain = domainService.getDomain(serverName);
//        Alias alias = aliasService.getAlias(domain, path);
//        Fragment fragment = alias.getFragment();
//
//        return "";
//    }

    @RequestMapping(path = "/fragment", params = {"id", "!path"})
    String getById(@RequestParam(name = "id") Long id, Model model) {
        Fragment fragment = fragmentService.getFragment(id);
        model.addAttribute("fragment", fragment);

        fragment.getAttributes().forEach((name, attribute) -> {
            model.addAttribute(name, attribute.getPayloads().get(0));
        });

        Type structure = fragmentService.getType(fragment);

        return structure.getName();
    }

    @RequestMapping(path = "/fragment", params = {"path", "!id"})
    String getByPath(@RequestParam(name = "path") String path,
                      HttpServletRequest request, Model model) {

        String serverName = request.getServerName();

//        Domain domain = domainService.getDomain(serverName);
//        Alias alias = aliasService.getAlias(domain, path);
//        Fragment fragment = alias.getFragment();

//        return getById(fragment.getId(), model);
        return null;
    }

    public FragmentController(FragmentService fragmentService, DomainService domainService) {
        this.fragmentService = fragmentService;
        this.domainService = domainService;
    }
}
