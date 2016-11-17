package biz.jovido.webseed.model.content.payload

import org.springframework.context.i18n.LocaleContextHolder

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Entity
class TextPayload extends LocalizablePayload<String> {

    @ElementCollection
    @CollectionTable(name = 'text_payload',
            joinColumns = @JoinColumn(name = 'payload_id'))
    @MapKeyColumn(name = 'language_tag')
    @Column(name = 'value')
    Map<Locale, String> localizedValues = new HashMap<>()

    @Override
    String getValue(Locale locale) {
        localizedValues.get(locale)
    }

    @Override
    void setValue(Locale locale, String localizedValue) {
        localizedValues.put(locale, localizedValue)
    }

    @Override
    String getValue() {
        def locale = LocaleContextHolder.locale
        localizedValues.get(locale)
    }

    @Override
    void setValue(String value) {
        def locale = LocaleContextHolder.locale
        localizedValues.put(locale, value)
    }
}
