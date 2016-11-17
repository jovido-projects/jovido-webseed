package biz.jovido.webseed.validation

import org.springframework.validation.Errors

/**
 *
 * @author Stephan Grundner
 */
interface Messages extends Errors {

    void highlightValue(String field, String messageCode)
    void highlightValue(String field, String messageCode, String defaultMessage)
    void highlightValue(String field, String messageCode, Object[] errorArgs, String defaultMessage)
}