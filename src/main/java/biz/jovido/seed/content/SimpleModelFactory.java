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

    private void apply(TextPayload payload, Map<String, Object> map) {
        map.put("value", payload.getText());
    }

    private void apply(YesNoPayload payload, Map<String, Object> map) {
        map.put("value", payload.isYes());
        map.put("yes", payload.isYes());
        map.put("no", !payload.isYes());
    }

    private void apply(LinkPayload payload, Map<String, Object> map) {
        map.put("url", payload.getUrl());
        map.put("text", payload.getText());
    }

    private void apply(ImagePayload payload,  Map<String, Object> map) {
        Image image = payload.getImage();
        if (image != null) {
            map.put("fileName", image.getFileName());
            map.put("alt", image.getAlt());
//                TODO Move to ItemService class:
            String url = String.format("/asset/files/%s/%s",
                    image.getUuid(),
                    image.getFileName());
            map.put("url", url);
        }
    }

    private void apply(SelectionPayload payload, Map<String, Object> map) {
        List<String> values = payload.getValues();
        SelectionAttribute attribute = (SelectionAttribute) itemService.getAttribute(payload);
        if (attribute.isMultiselect()) {
            map.put("values", Collections.unmodifiableList(values));
        } else {
            map.put("value", CollectionUtils.getFirst(values));
        }
    }

    private void apply(ItemPayload payload, Map<String, Object> map) {
        Item item = payload.getItem();
        map.putAll(toMap(item));
    }

    private Map<String, Object> toMap(Payload payload) {
        Map<String, Object> map = new HashMap<>();
        map.put("$payload", payload);

        switch (payload.getType()) {
            case TEXT:
                apply((TextPayload) payload, map);
                break;
            case YES_NO:
                apply((YesNoPayload) payload, map);
                break;
            case IMAGE:
                apply((ImagePayload) payload, map);
                break;
            case SELECTION:
                apply((SelectionPayload) payload, map);
                break;
            case LINK:
                apply((LinkPayload) payload, map);
                break;
            case ITEM:
                apply((ItemPayload) payload, map);
                break;
            default:
                throw new RuntimeException("Unexpected payload type: " + payload.getType());
        }

        return map;
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
