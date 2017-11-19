package biz.jovido.seed.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Stephan Grundner
 */
public class VisitorRequestInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(VisitorRequestInterceptor.class);

    private final VisitorService visitorService;
    private final HostService hostService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        StopWatch stopWatch = new StopWatch("request");
//        stopWatch.start();
        try {
            Visitor visitor = visitorService.getOrCreateVisitor(request, response);
            if (visitor != null) {
                request.setAttribute(Visitor.class.getName(), visitor);
            }

            Host host = hostService.getHost(request);
            if (host != null) {
                request.setAttribute(Host.class.getName(), host);
            }

            visitorService.toVisit(request);
        } catch (Exception e) {
            LOG.warn("Error on intercepting request", e);
        }
//        stopWatch.stop();
//        LOG.info(stopWatch.prettyPrint());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        request.setAttribute(Visitor.class.getName(), null);
    }

    public VisitorRequestInterceptor(VisitorService visitorService, HostService hostService) {
        this.visitorService = visitorService;
        this.hostService = hostService;
    }
}
