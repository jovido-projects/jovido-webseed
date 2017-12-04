package biz.jovido.seed.content.ui.source;

import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.source.AbstractSource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class ItemSource extends AbstractSource {

    public static abstract class PayloadProperty<V> extends AbstractProperty<V, ItemSource> {

        protected final PayloadGroup payloadGroup;

        protected abstract V toValue(Payload payload);
        protected abstract void applyValue(Payload payload, V v);

        @Override
        public List<V> getValues() {
            return payloadGroup.getPayloads().stream()
                    .map(this::toValue)
                    .collect(Collectors.toList());
        }

        @Override
        protected void setValue(int index, V value) {
            Payload payload = payloadGroup.getPayloads().get(index);
            applyValue(payload, value);
        }

        @Override
        public V removeValue(int index) {
            Payload payload = payloadGroup.removePayload(index);
            return toValue(payload);
        }

        public PayloadProperty(ItemSource source, PayloadGroup payloadGroup) {
            super(source, payloadGroup.getAttributeName());
            this.payloadGroup = payloadGroup;
        }
    }

    public static class LinkProperty extends PayloadProperty<Link> {

        @Override
        protected Link toValue(Payload payload) {
            return ((LinkPayload) payload);
        }

        @Override
        protected void applyValue(Payload payload, Link link) {
            LinkPayload linkPayload = (LinkPayload) payload;
            linkPayload.setText(link.getText());
            linkPayload.setUrl(link.getUrl());
        }

        public LinkProperty(ItemSource source, PayloadGroup payloadGroup) {
            super(source, payloadGroup);
        }
    }

    private final ItemService itemService;
    private final Item item;

    @Override
    public Property addProperty(String name) {
        return null;
    }

    public ItemSource(ItemService itemService, Item item) {
        this.itemService = itemService;
        this.item = item;
    }
}
