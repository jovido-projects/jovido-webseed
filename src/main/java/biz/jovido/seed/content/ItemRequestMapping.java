package biz.jovido.seed.content;

import biz.jovido.seed.net.Host;
import biz.jovido.seed.net.HostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
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
        String hostName = hostService.getHostName(request);
        Host host = hostService.getHost(hostName);
        if (host != null) {
            String path = request.getServletPath();
//            if ("/".equals(path)) {
//                path = host.getUri();
//            } else if (StringUtils.hasLength(path) && path.startsWith("/")) {
//                path = path.substring(1);
//            }

            List<Item> items = itemService.findAllPublishedItemsByPath(path);
            if (!items.isEmpty()) {
                if (items.size() > 1) {
                    LOG.warn("More than one published item found " +
                            "for path [{}] and host [{}]", path, hostName);
                }
//                TODO Find best matching item:
                Item item = items.get(0);
                LOG.debug("Item with id [{}] found " +
                        "for path [{}] and host [{}]", item.getId(), path, hostName);
                ItemForwardController controller =
                        new ItemForwardController(item);
                return new HandlerExecutionChain(controller);
            }
        }

        return null;
    }
}