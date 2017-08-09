package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Payload;
import biz.jovido.seed.content.model.Property;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class PropertyEditor {

    ItemForm form;
    String propertyName;

    private final Set<PayloadField> fields = new HashSet<>();

    public Property getProperty() {
        return form.getItem().getProperty(propertyName);
    }

    public List<PayloadField> getFields() {
        return fields.stream()
                .sorted(Comparator.comparingInt(PayloadField::getOrdinal))
                .collect(Collectors.toList());
    }

    public PayloadField getField(Payload payload) {
        return fields.stream()
                .filter(field -> field.getPayload() == payload)
                .findFirst()
                .orElse(null);
    }

    public void refresh() {
        Property property = getProperty();
        for (Payload payload : property.getPayloads()) {
            PayloadField field = getField(payload);
            if (field == null) {
                field = new TextPayloadField();
                field.editor = this;
                field.payload = payload;
                fields.add(field);
            }

        }
    }
}
