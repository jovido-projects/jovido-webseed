package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Payload;
import biz.jovido.seed.content.PayloadSequence;
import biz.jovido.seed.ui.Action;
import biz.jovido.seed.ui.Actions;
import biz.jovido.seed.ui.Field;

/**
 * @author Stephan Grundner
 */
public class PayloadField extends Field {

    public interface PayloadChangeHandler {

        void payloadChanged(PayloadField field, Payload previous);
    }

    private Payload payload;

    private Action moveUpAction;
    private Action moveDownAction;
    private Action removeAction;
    private Actions appendActions;

    private FragmentForm nestedForm;

    private PayloadChangeHandler payloadChangeHandler;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        Payload previous = this.payload;

        this.payload = payload;

        if (payloadChangeHandler != null) {
            payloadChangeHandler.payloadChanged(this, previous);
        }
    }

    public Action getMoveUpAction() {
        return moveUpAction;
    }

    public void setMoveUpAction(Action moveUpAction) {
        this.moveUpAction = moveUpAction;
    }

    public Action getMoveDownAction() {
        return moveDownAction;
    }

    public void setMoveDownAction(Action moveDownAction) {
        this.moveDownAction = moveDownAction;
    }

    public Action getRemoveAction() {
        return removeAction;
    }

    public void setRemoveAction(Action removeAction) {
        this.removeAction = removeAction;
    }

    public Actions getAppendActions() {
        return appendActions;
    }

    public void setAppendActions(Actions appendActions) {
        this.appendActions = appendActions;
    }

    public FragmentForm getNestedForm() {
        return nestedForm;
    }

    public void setNestedForm(FragmentForm nestedForm) {
        this.nestedForm = nestedForm;
    }

    public PayloadChangeHandler getPayloadChangeHandler() {
        return payloadChangeHandler;
    }

    public void setPayloadChangeHandler(PayloadChangeHandler payloadChangeHandler) {
        this.payloadChangeHandler = payloadChangeHandler;
    }

    public boolean isFirst() {
        return payload.getOrdinal() == 0;
    }

    public boolean isLast() {
        PayloadSequence sequence = payload.getSequence();
        return payload.getOrdinal() == sequence.size() - 1;
    }
}
