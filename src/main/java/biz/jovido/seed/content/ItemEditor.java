package biz.jovido.seed.content;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class ItemEditor extends NestedItemEditor {

    private final ItemService itemService;

    private final Map<String, PayloadField> fields = new HashMap<>();

    @Override
    public ItemService getItemService() {
        return itemService;
    }

    public Map<String, PayloadField> getFields() {
        return fields;
    }

    public void attachField(PayloadField field) {
        fields.put(field.getId(), field);
    }

    public void detachField(PayloadField field) {
        fields.remove(field.getId(), field);
    }

    public ItemEditor(ItemService itemService) {
        super(null);
        this.itemService = itemService;
    }
}
