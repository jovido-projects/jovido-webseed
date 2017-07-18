package biz.jovido.seed.content;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Stephan Grundner
 */
@Component
public class ContentAdministrationValidator implements Validator {

    private final ItemService itemService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ContentAdministration.class.isAssignableFrom(clazz);
    }

    public void validate(ContentAdministration administration, Errors errors) {

        FragmentValidator fragmentValidator = new FragmentValidator(itemService);

        errors.pushNestedPath("editor.fragment");
        fragmentValidator.validate(administration.getEditor().getFragment(), errors);
        errors.popNestedPath();

    }

    @Override
    public final void validate(Object target, Errors errors) {
        validate((ContentAdministration) target, errors);
    }

    public ContentAdministrationValidator(ItemService itemService) {
        this.itemService = itemService;
    }
}
