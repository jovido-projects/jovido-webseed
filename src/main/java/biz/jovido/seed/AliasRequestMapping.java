package biz.jovido.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stephan Grundner
 */
public class AliasRequestMapping extends AbstractHandlerMapping {

    @Autowired
    private AliasForwardController aliasForwardController;

    @Autowired
    private HostService hostService;

    @Autowired
    private AliasService aliasService;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        Host host = hostService.findHostByName(request.getServerName());
        if (host != null) {
            String path = request.getServletPath();
            Alias alias = aliasService.findAliasByPath(path, host);
            if (alias != null) {
                request.setAttribute(Alias.class.getName(), alias);
                return new HandlerExecutionChain(aliasForwardController);
            }
        }

        return null;
    }
}
