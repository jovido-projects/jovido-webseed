package biz.jovido.webseed.model.value

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
    Map<Locale, String> values = new HashMap<>()

    @Override
    String getValue(Locale locale) {
        values.get(locale)
    }

    @Override
    void setValue(Locale locale, String localizedValue) {
        values.put(locale, localizedValue)
    }

    @Override
    Map<Locale, String> getValue() {
        values
    }

    @Override
    void setValue(Map<Locale, String> value) {
        values = value
    }
}
