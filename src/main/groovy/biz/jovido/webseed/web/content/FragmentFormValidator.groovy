package biz.jovido.webseed.web.content

import biz.jovido.webseed.service.content.FragmentValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.SmartValidator

/**
 *
 * @author Stephan Grundner
 */
@Component
class FragmentFormValidator implements SmartValidator {

    protected final FragmentValidator fragmentValidator

    @Autowired
    FragmentFormValidator(FragmentValidator fragmentValidator) {
        this.fragmentValidator = fragmentValidator
    }

    @Override
    boolean supports(Class<?> clazz) {
        FragmentForm.isAssignableFrom(clazz)
    }

    @Override
    void validate(Object target, Errors errors, Object... validationHints) {
        def form = (FragmentForm) target
        errors.pushNestedPath('fragment')
        try {
            fragmentValidator.validate(form?.fragment, errors)
        } finally {
            errors.popNestedPath()
        }
    }

    @Override
    void validate(Object target, Errors errors) {
        validate(target, errors, [] as Object[])
    }
}
