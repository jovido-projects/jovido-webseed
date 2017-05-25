package biz.jovido.seed.content;

import biz.jovido.seed.hostname.Domain;
import biz.jovido.seed.hostname.DomainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stephan Grundner
 */
@Controller
public class FragmentController {

    private final FragmentService fragmentService;
    private final DomainService domainService;
    private final AliasService aliasService;

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

    @RequestMapping(path = "/fragment", params = {"id", "!alias"})
    String getById(@RequestParam(name = "id") Long id, Model model) {
        Fragment fragment = fragmentService.getFragment(id);
        model.addAttribute("fragment", fragment);

        fragment.getAttributes().forEach((name, attribute) -> {
            model.addAttribute(name, attribute.getPayloads().get(0));
        });

        Structure structure = fragmentService.getStructure(fragment);

        return structure.getName();
    }

    @RequestMapping(path = "/fragment", params = {"alias", "!id"})
    String getByAlias(@RequestParam(name = "alias") String path,
                      HttpServletRequest request, Model model) {

        String serverName = request.getServerName();

        Domain domain = domainService.getDomain(serverName);
        Alias alias = aliasService.getAlias(domain, path);
        Fragment fragment = alias.getFragment();

        return getById(fragment.getId(), model);
    }

    public FragmentController(FragmentService fragmentService, DomainService domainService, AliasService aliasService) {
        this.fragmentService = fragmentService;
        this.domainService = domainService;
        this.aliasService = aliasService;
    }
}
