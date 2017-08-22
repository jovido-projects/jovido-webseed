package biz.jovido.seed.util;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;

/**
 * @author Stephan Grundner
 */
public class ErrorUtils {

    public static void addToModel(Model model, Errors errors) {
        if (errors != null) {
            String objectName = errors.getObjectName();
            String attributeName = String.format("org.springframework.validation.BindingResult.%s", objectName);
            model.addAttribute(attributeName, errors);
        }
    }
}
