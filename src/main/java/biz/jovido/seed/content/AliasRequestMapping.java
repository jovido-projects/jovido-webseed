package biz.jovido.seed.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

/**
 * @author Stephan Grundner
 */
public class AliasRequestMapping extends AbstractHandlerMapping {

    private static final Logger LOG = LoggerFactory.getLogger(AliasRequestMapping.class);

    @Autowired
    private AliasForwardController aliasForwardController;

    @Autowired
    private HostService hostService;

    @Autowired
    private AliasService aliasService;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        String serverName = request.getServerName();
        String forwardedHost = request.getHeader("X-Forwarded-Host");

        LOG.debug("serverName: {}", serverName);
        LOG.debug("forwardedHost: {}", forwardedHost);

        String hostName;

        if (StringUtils.isEmpty(forwardedHost)) {
            hostName = serverName;
        } else {
            hostName = new URL(forwardedHost).getHost();
        }
        Host host = hostService.getHost(hostName);
        if (host != null) {
            String path = request.getServletPath();
            if ("/".equals(path)) {
                path = host.getPath();
            } else if (StringUtils.hasLength(path) && path.startsWith("/")) {
                path = path.substring(1);
            }

            Alias alias = aliasService.getAlias(host, path);
            if (alias != null) {
                request.setAttribute(Alias.class.getName(), alias);
                return new HandlerExecutionChain(aliasForwardController);
            }
        }

        return null;
    }
}