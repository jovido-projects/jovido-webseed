package biz.jovido.webseed.validation

import groovy.transform.InheritConstructors
import org.springframework.validation.BeanPropertyBindingResult

/**
 *
 * @author Stephan Grundner
 */
@InheritConstructors
class BeanPropertyBindingMessages extends BeanPropertyBindingResult implements BindingMessages {

    @Override
    void highlightValue(String field, String messageCode) {

    }

    @Override
    void highlightValue(String field, String messageCode, String defaultMessage) {

    }

    @Override
    void highlightValue(String field, String messageCode, Object[] errorArgs, String defaultMessage) {

    }
}
