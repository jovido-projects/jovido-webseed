package biz.jovido.webseed.model.content.payload

import biz.jovido.webseed.model.content.Payload

/**
 *
 * @author Stephan Grundner
 */
abstract class LocalizablePayload<T> extends Payload<T> {

    abstract T getValue(Locale locale)
    abstract void setValue(Locale locale, T localizedValue)
}
