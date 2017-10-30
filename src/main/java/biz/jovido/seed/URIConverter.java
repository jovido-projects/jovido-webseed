package biz.jovido.seed;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.net.URI;

/**
 * @author Stephan Grundner
 */
public class URIConverter implements AttributeConverter<URI, String> {

    @Override
    public String convertToDatabaseColumn(URI uri) {
        if (uri != null) {
            return uri.toString();
        }

        return null;
    }

    @Override
    public URI convertToEntityAttribute(String value) {
        if (StringUtils.hasLength(value)) {
            return URI.create(value);
        }

        return null;
    }
}
