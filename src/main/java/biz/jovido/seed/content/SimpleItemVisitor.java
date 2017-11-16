package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class SimpleItemVisitor implements ItemVisitor {

    @Override
    public ItemVisitResult visitItem(Item item) {
        return ItemVisitResult.CONTINUE;
    }

    @Override
    public ItemVisitResult visitPayloadGroup(PayloadGroup payloadGroup) {
        return ItemVisitResult.CONTINUE;
    }

    @Override
    public ItemVisitResult visitPayload(Payload payload) {
        return ItemVisitResult.CONTINUE;
    }
}
