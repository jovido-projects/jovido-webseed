package biz.jovido.seed.content;

import biz.jovido.seed.MessageSourceProvider;
import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class ItemEditor extends NestedItemEditor implements MessageSourceProvider {

    private MessageSource messageSource;
    private final ItemService itemService;

    private final Map<String, PayloadField> fields = new HashMap<>();

    @Override
    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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

    public String getPath() {
        return getItem().getPath();
    }

    public void setPath(String path) {
        getItem().setPath(path);
    }

    public Long getLoaded() {
        Item item = getItem();
        if (item != null) {
            return item.getId();
        }

        return null;
    }

    public ItemEditor(ItemService itemService) {
        super(null);
        this.itemService = itemService;
    }
}
