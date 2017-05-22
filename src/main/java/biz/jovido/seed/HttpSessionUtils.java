package biz.jovido.seed;

import org.springframework.session.web.http.HttpSessionManager;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stephan Grundner
 */
@Deprecated
public class HttpSessionUtils {

    public static HttpSessionManager getSessionManager(HttpServletRequest request) {
        return (HttpSessionManager) request.getAttribute(HttpSessionManager.class.getName());
    }
}
