package biz.jovido.seed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Stephan Grundner
 */
@Controller
public class PingController {

    @GetMapping(path = "ping")
    @ResponseBody
    protected boolean ping() {
        return true;
    }
}
