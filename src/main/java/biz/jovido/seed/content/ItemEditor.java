package biz.jovido.seed.content;

import biz.jovido.seed.uimodel.Actions;
import biz.jovido.seed.uimodel.InvalidationListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    public static class PayloadFieldGroup implements ItemChangeListener {

        private final ItemEditor editor;
        private final String attributeName;
        private Actions actions;
        private String payloadTemplate;

        private InvalidationListener<PayloadFieldGroup> invalidationListener;

        private Item getItem() {
            return editor.getItem();
        }

        public PayloadGroup getPayloadGroup() {
            return getItem().getPayloadGroup(attributeName);
        }

        public Actions getActions() {
            if (actions == null) {
                actions = new Actions();
            }

            return actions;
        }

        public void setActions(Actions actions) {
            this.actions = actions;
        }

        public String getPayloadTemplate() {
            return payloadTemplate;
        }

        public void setPayloadTemplate(String payloadTemplate) {
            this.payloadTemplate = payloadTemplate;
        }

        public InvalidationListener<PayloadFieldGroup> getInvalidationListener() {
            return invalidationListener;
        }

        public void setInvalidationListener(InvalidationListener<PayloadFieldGroup> invalidationListener) {
            this.invalidationListener = invalidationListener;
        }

        void invalidate() {
            invalidationListener.invalidated(this);
        }

        @Override
        public void payloadAdded(Item item, Payload payload) {
            invalidate();
        }

        @Override
        public void payloadRemoved(Item item, Payload payload) {
            invalidate();
        }

        public PayloadFieldGroup(ItemEditor editor, String attributeName) {
            this.editor = editor;
            this.attributeName = attributeName;
        }
    }

    private Item item;
    private InvalidationListener<ItemEditor> invalidationListener;

    private final Map<UUID, PayloadFieldGroup> fieldGroups = new HashMap<>();

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;

        fieldGroups.clear();

        invalidationListener.invalidated(this);

        fieldGroups.forEach((k, v) -> {
            v.invalidate();
        });
    }

    public InvalidationListener<ItemEditor> getInvalidationListener() {
        return invalidationListener;
    }

    public void setInvalidationListener(InvalidationListener<ItemEditor> invalidationListener) {
        this.invalidationListener = invalidationListener;
    }

    public void addFieldGroup(PayloadFieldGroup fieldGroup) {
        fieldGroups.put(fieldGroup.getPayloadGroup().getUuid(), fieldGroup);
    }

    public PayloadFieldGroup getFieldGroup(UUID uuid) {
        return fieldGroups.get(uuid);
    }

    public PayloadFieldGroup getFieldGroup(PayloadGroup payloadGroup) {
        return getFieldGroup(payloadGroup.getUuid());
    }
}
