package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.FragmentValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Stephan Grundner
 */
@Component
public class ItemAdministrationValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ItemAdministration.class.isAssignableFrom(clazz);
    }

    public void validate(ItemAdministration administration, Errors errors) {

        FragmentValidator fragmentValidator = new FragmentValidator();

        errors.pushNestedPath("editor.fragment");
        fragmentValidator.validate(administration.getEditor().getFragment(), errors);
        errors.popNestedPath();

    }

    @Override
    public final void validate(Object target, Errors errors) {
        validate((ItemAdministration) target, errors);
    }
}
