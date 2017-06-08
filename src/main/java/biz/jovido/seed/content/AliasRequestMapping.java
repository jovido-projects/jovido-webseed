package biz.jovido.seed.content;

import biz.jovido.seed.Domain;
import biz.jovido.seed.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stephan Grundner
 */
public class AliasRequestMapping extends AbstractHandlerMapping {

    @Autowired
    private DomainService domainService;

    @Autowired
    private AliasForwardController aliasForwardController;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        Domain domain = domainService.getDomain(request);
        if (domain != null) {
            String path = request.getServletPath();
//            Alias alias = aliasService.getAlias(domain, path);
//            if (alias != null) {
//                return new HandlerExecutionChain(aliasForwardController);
//            }
        }

        return null;
    }
}
