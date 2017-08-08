package biz.jovido.seed.content;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class ItemEditor implements ItemChangeListener, RelationChangeListener {

    private final ItemService itemService;

    private Item item;

    private final Map<String, Field> fields = new HashMap<>();

    public Item getItem() {
        return item;
    }

    private void applyField(String attributeName) {
        Field field = fields.get(attributeName);
        if (field == null) {
            field = new TextField();
            field.editor = this;

            Structure structure = getStructure();
            field.attribute = structure.getAttribute(attributeName);
            fields.put(attributeName, field);
        }
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.removeChangeListener(this);
        }

        this.item = item;

        item.addChangeListener(this);

        Structure structure = getStructure();
        for (Attribute attribute : structure.getAttributes()) {
            if (attribute instanceof RelationAttribute) {
                RelationPayload payload = (RelationPayload) item.getPayload(attribute.getName());
                Relation relation = payload.getValue();
                relation.addChangeListener(this);
                relation.addChangeListener(new RelationChangeListener() {
                    @Override
                    public void relationChange(RelationChangeEvent event) {
                        System.out.println("Relation changed: " + event.getRelation());
                    }
                });
            }
            applyField(attribute.getName());
        }
    }

    public Map<String, Field> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public Structure getStructure() {
        return itemService.getStructure(item);
    }

    public void create(String structureName, Locale locale) {
        Item item = itemService.createItem(structureName, locale);

        setItem(item);
    }

    public void append(String attributeName, String structureName) {}

    public void save() {}

    public ItemEditor(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void payloadChange(PayloadChangeEvent event) {
        applyField(event.getAttributeName());
    }

    @Override
    public void relationChange(RelationChangeEvent event) {

    }

    @Override
    public void targetAdded(TargetAddedEvent event) {

    }

    @Override
    public void targetMoved(TargetMovedEvent event) {

    }
}
