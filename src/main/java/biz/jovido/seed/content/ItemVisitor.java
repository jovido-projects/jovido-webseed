package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface ItemVisitor {

    ItemVisitResult visitItem(Item item);
    ItemVisitResult visitPayload(Payload payload);
}
