package biz.jovido.webseed.model.value

/**
 *
 * @author Stephan Grundner
 */
abstract class LocalizablePayload<T> extends Payload<Map<Locale, T>> {

    abstract T getValue(Locale locale)
    abstract void setValue(Locale locale, T localizedValue)
}
