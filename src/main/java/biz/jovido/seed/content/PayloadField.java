package biz.jovido.seed.content;

import biz.jovido.seed.ui.Action;
import biz.jovido.seed.ui.Field;

/**
 * @author Stephan Grundner
 */
public class PayloadField extends Field implements PayloadChangeListener {

    private final PayloadSequenceField parent;
    private final Payload payload;

    private Action moveUpAction;
    private Action moveDownAction;
    private Action removeAction;

    private FragmentForm nestedForm;

    public PayloadSequenceField getParent() {
        return parent;
    }

    protected void updateActions() {
        Action moveUp = getMoveUpAction();
        moveUp.setDisabled(isFirst());

        Action moveDown = getMoveDownAction();
        moveDown.setDisabled(isLast());
    }

    @Override
    public void payloadChanged(PayloadChange change) {
        if (change instanceof PayloadChange.OrdinalSet) {

            PayloadSequenceField parent = getParent();
            for (PayloadField field : parent.getFields()) {
                field.updateActions();
            }
        }
    }

    public Payload getPayload() {
        return payload;
    }

    public int getOrdinal() {
        return getPayload().getOrdinal();
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

    public FragmentForm getNestedForm() {
        return nestedForm;
    }

    public void setNestedForm(FragmentForm nestedForm) {
        this.nestedForm = nestedForm;
    }

    public boolean isFirst() {
        return payload.getOrdinal() == 0;
    }

    public boolean isLast() {
        PayloadSequence sequence = payload.getSequence();
        return payload.getOrdinal() == sequence.size() - 1;
    }

    public PayloadField(PayloadSequenceField parent, Payload payload) {
        this.parent = parent;
        this.payload = payload;

        payload.addChangeListener(this);
    }
}
