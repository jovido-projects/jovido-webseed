package biz.jovido.seed;

import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Stephan Grundner
 */
public class HttpSessionAliasPublisher extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSessionManager sessionManager = HttpSessionUtils.getSessionManager(request);
        if (sessionManager != null) {
            String sessionAlias = sessionManager.getCurrentSessionAlias(request);
            if (!StringUtils.isEmpty(sessionAlias)) {
                modelAndView.addObject("_s", sessionAlias);
            }
        }
    }
}
