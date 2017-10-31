package biz.jovido.seed.content;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Stephan Grundner
 */
public class ItemForwardController implements Controller {

    private final Item item;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forward:/item?id=" + item.getId());
        return modelAndView;
    }

    public ItemForwardController(Item item) {
        this.item = item;
    }
}
