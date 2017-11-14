package biz.jovido.seed.content;

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
        for (Payload payload : payloadGroup.getPayloads()) {
            ItemVisitResult result = walkPayload(payload, visitor);
            if (result != ItemVisitResult.CONTINUE) {
                return result;
            }
        }

        return ItemVisitResult.CONTINUE;
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
}
