package biz.jovido.seed.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class ItemRequestMapping extends AbstractHandlerMapping {

    private static final Logger LOG = LoggerFactory.getLogger(ItemRequestMapping.class);

    @Autowired
    private HostService hostService;

    @Autowired
    private ItemService itemService;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {

        String hostName = request.getHeader("X-Forwarded-Host");
        if (StringUtils.isEmpty(hostName)) {
            hostName = request.getServerName();
        } else {
            LOG.info("X-Forwarded-Host: {}", hostName);
        }

        if (!"localhost".equals(hostName)) {
            hostName = hostService.getHostName(hostName);
            LOG.info("Host: {}", hostName);
        } else {
            LOG.info("Host: localhost");
        }

        Host host = hostService.getHost(hostName);
        if (host != null) {
            String path = request.getServletPath();
            if ("/".equals(path)) {
                path = host.getPath();
                LOG.info("Request path: /");
            } else if (StringUtils.hasLength(path) && path.startsWith("/")) {
                path = path.substring(1);
                LOG.info("Request path: {}", path);
            }

            List<Item> items = itemService.findAllPublishedItemByPath(path);
            if (!items.isEmpty()) {
                if (items.size() > 1) {
                    LOG.warn("More than one published item found for path {}", path);
                }
//                TODO Find best matching item:
                Item item = items.get(0);
                LOG.info("Item {} found for path {}", item.getId(), path);
                ItemForwardController controller =
                        new ItemForwardController(item);
                return new HandlerExecutionChain(controller);
            } else {
                LOG.warn("No item found for path {}", path);
            }
        }

        return null;
    }
}