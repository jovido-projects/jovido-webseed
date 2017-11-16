package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface ItemVisitor {

    ItemVisitResult visitItem(Item item);
    ItemVisitResult visitPayloadGroup(PayloadGroup payloadGroup);
    ItemVisitResult visitPayload(Payload payload);
}
