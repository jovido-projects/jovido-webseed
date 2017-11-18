package biz.jovido.seed.content;

import biz.jovido.seed.content.frontend.ItemValues;
import biz.jovido.seed.content.frontend.ValueMap;
import biz.jovido.seed.content.frontend.ValuesList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class ItemUtils {

    @Deprecated
    public static boolean areTheSame(Item one, Item other) {
        if (one == null || other == null) {
            return false;
        }

        if (one == other) {
            return true;
        }

        return one.getId().equals(other.getId());
    }

    public static ItemVisitResult walkPayload(Payload payload, ItemVisitor visitor) {
        ItemVisitResult result = visitor.visitPayload(payload);
        if (result == ItemVisitResult.CONTINUE) {
            if (payload instanceof ItemRelation) {
                Item item = ((ItemRelation) payload).getTarget();
                result = walkItem(item, visitor);
            }
        }

        return result;
    }

    public static ItemVisitResult walkItem(Item item, ItemVisitor visitor) {
        ItemVisitResult result = visitor.visitItem(item);
        if (result == ItemVisitResult.CONTINUE) {
            for (Payload payload : item.getPayloads()) {
                result = walkPayload(payload, visitor);
                if (result != ItemVisitResult.CONTINUE) {
                    return result;
                }
            }
        }

        return ItemVisitResult.CONTINUE;
    }

    public static Payload findPayload(Item item, UUID payloadUuid) {
        final AtomicReference<Payload> found = new AtomicReference<>();
        walkItem(item, new SimpleItemVisitor() {
            @Override
            public ItemVisitResult visitPayload(Payload payload) {
                if (payload.getUuid().equals(payloadUuid)) {
                    found.set(payload);
                    return ItemVisitResult.TERMINATE;
                }

                return ItemVisitResult.CONTINUE;
            }
        });

        return found.get();
    }

    public static Item findItem(Item item, UUID itemUuid) {
        final AtomicReference<Item> found = new AtomicReference<>();
        walkItem(item, new SimpleItemVisitor() {
            @Override
            public ItemVisitResult visitItem(Item item) {
                if (item.getUuid().equals(itemUuid)) {
                    found.set(item);
                    return ItemVisitResult.TERMINATE;
                }

                return ItemVisitResult.CONTINUE;
            }
        });

        return found.get();
    }

    public static List<Payload> getPayloads(Item item, String attributeName) {
        return item.getPayloads().stream()
                .filter(it -> it.getAttributeName().equals(attributeName))
                .sorted(Comparator.comparingInt(Payload::getOrdinal))
                .collect(Collectors.toList());
    }

    private static ValuesList toList(Item item, String attributeName) {
        ValuesList list = new ValuesList(attributeName);
        for (Payload payload : getPayloads(item, attributeName)) {
            if (payload instanceof ItemRelation) {
                Item relatedItem = ((ItemRelation) payload).getTarget();
                list.add(toModel(relatedItem));
            } else {

                ValueMap map = new ValueMap();

                if (payload instanceof ImageRelation) {
                    Image image = ((ImageRelation) payload).getTarget();
                    map.put("fileName", Optional.ofNullable(image).map(Image::getFileName));
                    map.put("alt", Optional.ofNullable(image).map(Image::getAlt));
                    map.put("id", Optional.ofNullable(image).map(Image::getId));
                    String url = image != null ? String.format("/asset/files/%s/%s",
                            image.getUuid(),
                            image.getFileName()) : null;
                    map.put("url", url);
                } else if (payload instanceof Link) {
                    Link link = ((Link) payload);
                    map.put("uri", link.getUri());
                    map.put("url", link.getUri());
                    map.put("target", link.getTarget());
                    map.put("value", link.getText());
                } else if (payload instanceof Text) {
                    Text text = ((Text) payload);
                    map.put("value", text.getValue());
                } else if (payload instanceof YesNo) {
                    YesNo yesNo = ((YesNo) payload);
                    map.put("value", yesNo.getValue());
                    map.put("yes", yesNo.getValue());
                    map.put("no", !yesNo.getValue());
                } else {
                    throw new RuntimeException("Unexpected payload: " + payload.getClass());
                }

                list.add(map);
            }
        }
        return list;
    }

    public static ItemValues toModel(Item item) {
        ItemValues values = new ItemValues(item);
        for (String attributeName : item.getAttributeNames()) {
            values.put(attributeName, toList(item, attributeName));
        }

        return values;
    }
}
