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
        }
        hostName = hostService.getHostName(hostName);
        Host host = hostService.getHost(hostName);
        if (host != null) {
            String path = request.getServletPath();
            if ("/".equals(path)) {
                path = host.getPath();
            } else if (StringUtils.hasLength(path) && path.startsWith("/")) {
                path = path.substring(1);
            }

            List<Item> items = itemService.findAllPublishedItemByPath(path);
            if (!items.isEmpty()) {
                if (items.size() > 1) {
                    LOG.warn("More than one published item found for path [{}]", path);
                }
//                TODO Find best matching item:
                Item item = items.get(0);
                ItemForwardController controller =
                        new ItemForwardController(item);
                return new HandlerExecutionChain(controller);
            }
        }

        return null;
    }
}