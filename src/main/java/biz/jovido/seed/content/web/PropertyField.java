package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Attribute;
import biz.jovido.seed.content.model.Property;
import biz.jovido.seed.content.model.Structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class PropertyField {

    String name;
    ItemForm form;

    private Property property;

    private final List<Payload> payloads = new ArrayList<>();

    public String getName() {
        return name;
    }

    public ItemForm getForm() {
        return form;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public List<Payload> getPayloads() {
        return Collections.unmodifiableList(payloads);
    }

    public void addPayload(Payload payload) {
        if (payloads.add(payload)) {
            payload.field = this;
        }
    }

    public Attribute getAttribute() {
        Structure structure = form.editor.getStructure();
        return structure.getAttribute(property.getName());
    }
}
