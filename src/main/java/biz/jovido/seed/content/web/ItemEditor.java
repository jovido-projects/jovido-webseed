package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.*;
import biz.jovido.seed.web.Editor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class ItemEditor implements Editor<PropertyField>, PropertyChangeListener {

    private final ItemService itemService;

    Item item;

    private final Map<String, PropertyField> fields = new HashMap<>();

    public Map<String, PropertyField> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public PropertyField getField(String name) {
        return fields.get(name);
    }

    public void setField(String name, PropertyField field) {
        PropertyField replaced = fields.put(name, field);

        if (replaced != null) {
            replaced.editor = null;
            replaced.name = null;
        }

        if (field != null) {
            field.editor = this;
            field.name = name;
        }
    }

    public Item getItem() {
        return item;
    }

    public Structure getStructure() {
        return itemService.getStructure(item);
    }

    private RelationPropertyField createField(RelationAttribute attribute) {
        RelationPropertyField field = new RelationPropertyField();
        for (String acceptedStructureName : attribute.getAcceptedStructureNames()) {
            Structure acceptedStructure = itemService.getStructure(acceptedStructureName);
            field.getAllowedStructures().add(acceptedStructure);
        }

        return field;
    }

    private void applyField(Property property) {
        Structure structure = getStructure();
        Attribute attribute = structure.getAttribute(
                property.getName());

        property.addChangeListener(this);
        PropertyField field;// = new PropertyField();

        if (attribute instanceof RelationAttribute) {


            field = createField((RelationAttribute) attribute);
        } else {
            field = new PropertyField();
        }

        for (Payload value : property.getPayloads()) {
            PayloadValue payload = new PayloadValue();
            payload.setPayload(value);
            field.addValue(payload);
        }
        field.setProperty(property);
        setField(attribute.getName(), field);
    }

    public void create(String structureName, Locale locale) {
        item = itemService.createItem(structureName, locale);

        Structure structure = getStructure();
        for (Attribute attribute : structure.getAttributes()) {
            Property property = item.getProperty(attribute.getName());
            applyField(property);
        }
    }

    @Override
    public void payloadAdded(PayloadAddedEvent event) {
        Property property = event.getProperty();
        applyField(property);
    }

    @Override
    public void payloadRemoved(PayloadRemovedEvent event) {
        Property property = event.getProperty();
        PropertyField field = getField(property.getName());
        field.removeValue(event.getIndex());
    }

    @Override
    public void payloadMoved(PayloadMovedEvent event) {

    }

    public ItemEditor(ItemService itemService) {
        this.itemService = itemService;
    }
}
