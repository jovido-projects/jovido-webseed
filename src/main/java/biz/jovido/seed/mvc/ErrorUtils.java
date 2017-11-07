package biz.jovido.seed.mvc;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class ErrorUtils {

    private static final String KEY = "org.springframework.validation.BindingResult[]";

    @SuppressWarnings("unchecked")
    public static void redirectErrors(RedirectAttributes redirectAttributes, Errors errors) {
        Map<String, ?> flashAttributes = redirectAttributes.getFlashAttributes();
        Map<String, Errors> errorsByObjectName = (Map<String, Errors>) flashAttributes.get(KEY);
        if (errorsByObjectName == null) {
            errorsByObjectName = new HashMap<>();
            redirectAttributes.addFlashAttribute(KEY, errorsByObjectName);
        }

        errorsByObjectName.put(errors.getObjectName(), errors);
    }

    @SuppressWarnings("unchecked")
    public static void applyRedirectedErrors(Model model) {
        Map<String, Object> modelMap = model.asMap();
        Map<String, Errors> errorsByObjectName = (Map<String, Errors>) modelMap.get(KEY);

        if (errorsByObjectName != null) {
            errorsByObjectName.forEach((objectName, errors) -> {
                String attributeName = String.format("org.springframework.validation.BindingResult.%s", objectName);
                modelMap.put(attributeName, errors);
            });
        }
    }
}
