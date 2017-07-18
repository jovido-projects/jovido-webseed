package biz.jovido.seed;

import org.springframework.util.MimeType;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

/**
 * @author Stephan Grundner
 */
public class MimeTypeConverter implements AttributeConverter<MimeType, String> {

    @Override
    public String convertToDatabaseColumn(MimeType mimeType) {
        if (mimeType != null) {
            return mimeType.toString();
        }

        return null;
    }

    @Override
    public MimeType convertToEntityAttribute(String value) {
        if (StringUtils.hasText(value)) {
            return MimeType.valueOf(value);
        }

        return null;
    }
}
