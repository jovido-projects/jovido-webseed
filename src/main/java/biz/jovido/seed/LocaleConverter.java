package biz.jovido.seed;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public class LocaleConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale locale) {
        if (locale != null) {
            return locale.toLanguageTag();
        }

        return null;
    }

    @Override
    public Locale convertToEntityAttribute(String languageTag) {
        if (StringUtils.hasLength(languageTag)) {
            return Locale.forLanguageTag(languageTag);
        }

        return null;
    }
}
