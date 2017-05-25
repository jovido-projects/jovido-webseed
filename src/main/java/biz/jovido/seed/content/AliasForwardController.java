package biz.jovido.seed.content;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Stephan Grundner
 */
@Component
public class AliasForwardController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        String path = request.getServletPath();
        path = UriUtils.encodePathSegment(path, "UTF-8");
        modelAndView.setViewName("forward:/fragment?alias=" + path);
        return modelAndView;
    }
}
