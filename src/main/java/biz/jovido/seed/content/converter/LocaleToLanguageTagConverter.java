package biz.jovido.seed.content.converter;

import javax.persistence.AttributeConverter;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public final class LocaleToLanguageTagConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale locale) {
        return locale.toLanguageTag();
    }

    @Override
    public Locale convertToEntityAttribute(String languageTag) {
        return Locale.forLanguageTag(languageTag);
    }
}
