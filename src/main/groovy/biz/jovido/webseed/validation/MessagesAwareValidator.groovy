package biz.jovido.webseed.validation

import org.springframework.validation.Validator

/**
 *
 * @author Stephan Grundner
 */
interface MessagesAwareValidator extends Validator {

    void validate(Object target, Messages messages)
}