package biz.jovido.seed;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
public class UUIDConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        if (uuid != null) {
            return uuid.toString();
        }

        return null;
    }

    @Override
    public UUID convertToEntityAttribute(String text) {
        if (StringUtils.hasLength(text)) {
            return UUID.fromString(text);
        }

        return null;
    }
}
