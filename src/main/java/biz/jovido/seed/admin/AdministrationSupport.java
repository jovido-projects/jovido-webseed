package biz.jovido.seed.admin;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Stephan Grundner
 */
@ControllerAdvice
public class AdministrationSupport {

    @ModelAttribute
    protected Administration administration() {
        return new Administration();
    }
}
