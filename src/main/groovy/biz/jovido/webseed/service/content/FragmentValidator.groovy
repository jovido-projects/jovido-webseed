package biz.jovido.webseed.service.content

import biz.jovido.webseed.model.content.Fragment
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.validation.Errors
import org.springframework.validation.SmartValidator

/**
 *
 * @author Stephan Grundner
 */
@Component
class FragmentValidator implements SmartValidator {

    @Override
    boolean supports(Class<?> clazz) {
        Fragment.isAssignableFrom(clazz)
    }

    @Override
    void validate(Object target, Errors errors, Object... validationHints) {
        def fragment = (Fragment) target

        fields: for (def field :  fragment.type.fields.values()) {
            def constraint = field.constraint
            def attribute = fragment.getAttribute(field)
            if (attribute == null) {

                if (constraint.minValues > 0) {
                    errors.rejectValue("attributes[${field.name}]", 'not.empty', 'must not be empty')
                }
                continue fields
            }

            for (def payload : attribute.payloads) {

                def value = payload.value
                if (StringUtils.isEmpty(value)) {

                    if (!constraint.nullable) {
                        errors.rejectValue("attributes[${field.name}][${payload.ordinal}]",
                                'not.empty', 'must not be empty')
                    }
                }

            }
        }
    }

    @Override
    void validate(Object target, Errors errors) {
        validate(target, errors, [] as Object[])
    }
}
