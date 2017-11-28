package biz.jovido.seed.content;

import biz.jovido.seed.util.CollectionUtils;
import biz.jovido.seed.util.NullSafeList;
import org.springframework.ui.Model;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class SimpleModelFactory implements ModelFactory {

    private final ItemService itemService;

    @Override
    public boolean supports(Structure structure) {
        return structure != null;
    }

    private Map<String, Object> apply(TextPayload payload) {
        Map<String, Object> map = new HashMap<>();
        map.put("$payload", payload);

        map.put("value", payload.getText());

        return map;
    }

    private Map<String, Object> apply(YesNoPayload payload) {
        Map<String, Object> map = new HashMap<>();
        map.put("$payload", payload);

        map.put("value", payload.isYes());
        map.put("yes", payload.isYes());
        map.put("no", !payload.isYes());

        return map;
    }

    private Map<String, Object> apply(LinkPayload payload) {
        Map<String, Object> map = new HashMap<>();
        map.put("$payload", payload);

        map.put("url", payload.getUrl());
        map.put("text", payload.getText());

        return map;
    }

    private Map<String, Object> apply(ImagePayload payload) {
        Map<String, Object> map = null;

        OriginalImage image = payload.getImage();
        if (image != null) {
            map = new HashMap<>();
            map.put("$payload", payload);
            map.put("fileName", image.getFileName());
            map.put("alt", image.getAlt());
//                TODO Move to ItemService class:
            String url = String.format("/asset/files/%s/%s",
                    image.getUuid(),
                    image.getFileName());
            map.put("url", url);

            Map<String, Object> small = new HashMap<>();
            small.put("url", String.format("/asset/images/%s/%s?style=small",
                    image.getUuid(),
                    image.getFileName()));
            map.put("small", small);
        }

        return map;
    }

    private Map<String, Object> apply(SelectionPayload payload) {
        Map<String, Object> map = new HashMap<>();
        map.put("$payload", payload);

        List<String> values = payload.getValues();
        SelectionAttribute attribute = (SelectionAttribute) itemService.getAttribute(payload);
        if (attribute.isMultiselect()) {
            map.put("values", Collections.unmodifiableList(values));
        } else {
            map.put("value", CollectionUtils.getFirst(values));
        }

        return map;
    }

    private Map<String, Object> apply(ItemPayload payload) {
        Map<String, Object> map = new HashMap<>();
        map.put("$payload", payload);

        Item item = payload.getItem();
        map.putAll(toMap(item));

        return map;
    }

    private Map<String, Object> toMap(Payload payload) {
        switch (payload.getType()) {
            case TEXT:
                return apply((TextPayload) payload);
            case YES_NO:
                return apply((YesNoPayload) payload);
            case IMAGE:
                return apply((ImagePayload) payload);
            case SELECTION:
                return apply((SelectionPayload) payload);
            case LINK:
                return apply((LinkPayload) payload);
            case ITEM:
                return apply((ItemPayload) payload);
            default:
                throw new RuntimeException("Unexpected payload type: " + payload.getType());
        }
    }

    private List<Object> toList(List<Payload> payloads) {
        List<Object> list = new NullSafeList<>();
        for (Payload payload : payloads) {
            list.add(toMap(payload));
        }

        return list;
    }

    private Map<String, Object> toMap(Item item) {
        Map<String, Object> map = new HashMap<>();
        map.put("$item", item);

        Structure structure = itemService.getStructure(item);
        map.put("template", structure.getTemplate());

        if (structure.isPublishable()) {
            map.put("url", itemService.getUrl(item));
        }

        for (String attributeName : structure.getAttributeNames()) {
            Attribute attribute = structure.getAttribute(attributeName);
            if (attribute.getCapacity() == 1) {
                Payload payload = itemService.getPayload(item, attributeName);
                if (payload != null) {
                    map.put(attributeName, toMap(payload));
                }
            } else {
                List<Payload> payloads = itemService.getPayloads(item, attributeName);
                map.put(attributeName, toList(payloads));
            }
        }

        return map;
    }

    @Override
    public void apply(Item item, Model model) {
        model.addAllAttributes(toMap(item));
    }

    public SimpleModelFactory(ItemService itemService) {
        this.itemService = itemService;
    }
}
