package biz.jovido.seed.content;

import biz.jovido.seed.content.frontend.ItemValues;
import biz.jovido.seed.content.frontend.ValueMap;
import biz.jovido.seed.content.frontend.ValuesList;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Stephan Grundner
 */
public class ItemUtils {

    public static Item getCurrent(Item item) {
        if (item != null) {
            Leaf history = item.getLeaf();
            if (history != null) {
                return history.getCurrent();
            }
        }

        return null;
    }

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

    public static ItemVisitResult walkPayloadGroup(PayloadGroup payloadGroup, ItemVisitor visitor) {
        ItemVisitResult result = visitor.visitPayloadGroup(payloadGroup);
        if (result != ItemVisitResult.TERMINATE) {
            for (Payload payload : payloadGroup.getPayloads()) {
                result = walkPayload(payload, visitor);
                if (result != ItemVisitResult.CONTINUE) {
                    return result;
                }
            }
        }

        return result;
    }

    public static ItemVisitResult walkItem(Item item, ItemVisitor visitor) {
        for (PayloadGroup payloadGroup : item.getPayloadGroups().values()) {
            ItemVisitResult result = walkPayloadGroup(payloadGroup, visitor);
            if (result != ItemVisitResult.CONTINUE) {
                return result;
            }
        }

        return ItemVisitResult.CONTINUE;
    }

    public static PayloadGroup findPayloadGroup(Item item, UUID payloadGroupUuid) {
        final AtomicReference<PayloadGroup> found = new AtomicReference<>();
        walkItem(item, new SimpleItemVisitor() {
            @Override
            public ItemVisitResult visitPayloadGroup(PayloadGroup payloadGroup) {
                if (payloadGroup.getUuid().equals(payloadGroupUuid)) {
                    found.set(payloadGroup);
                    return ItemVisitResult.TERMINATE;
                }

                return super.visitPayloadGroup(payloadGroup);
            }
        });

        return found.get();
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

                return super.visitPayload(payload);
            }
        });

        return found.get();
    }

    @Deprecated
    private static ValuesList toList(PayloadGroup payloadGroup) {
        ValuesList list = new ValuesList(payloadGroup);
        for (Payload payload : payloadGroup.getPayloads()) {
            if (payload instanceof ItemRelation) {
                Item item = ((ItemRelation) payload).getTarget();
                list.add(toModel(item));
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

    @Deprecated
    public static ItemValues toModel(Item item) {
        ItemValues values = new ItemValues(item);
        for (String attributeName : item.getAttributeNames()) {
            PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
            values.put(attributeName, toList(payloadGroup));
        }

        return values;
    }
}
