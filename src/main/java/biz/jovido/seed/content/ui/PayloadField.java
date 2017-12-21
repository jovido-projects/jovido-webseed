package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Payload;
import biz.jovido.seed.content.PayloadSequence;
import biz.jovido.seed.content.event.OrdinalSet;
import biz.jovido.seed.content.event.PayloadChange;
import biz.jovido.seed.content.event.PayloadChangeListener;
import biz.jovido.seed.ui.Action;
import biz.jovido.seed.ui.Field;
import biz.jovido.seed.ui.StaticText;

/**
 * @author Stephan Grundner
 */
public class PayloadField extends Field {

    private Payload payload;

    private Action moveUpAction;
    private Action moveDownAction;
    private Action removeAction;

    private FragmentForm nestedForm;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;

        setMoveUpAction(new Action());
        setMoveDownAction(new Action());
        setRemoveAction(new Action());

        Action moveUp = getMoveUpAction();
        moveUp.setUrl("/admin/fragment/move-up?field=" + getId());
        moveUp.setText(new StaticText("Move Up"));
        moveUp.setDisabled(isFirst());

        Action moveDown = getMoveDownAction();
        moveDown.setUrl("/admin/fragment/move-down?field=" + getId());
        moveDown.setText(new StaticText("Move Down"));
        moveDown.setDisabled(isLast());

        Action remove = getRemoveAction();
        remove.setUrl("/admin/fragment/remove?field=" + getId());
        remove.setText(new StaticText("Remove"));

        if (payload != null) {
            payload.addChangeListener(new PayloadChangeListener() {
                @Override
                public void payloadChanged(PayloadChange change) {
                    if (change instanceof OrdinalSet) {
                        OrdinalSet ordinalSet = (OrdinalSet) change;
                        int ordinal = ordinalSet.getPayload().getOrdinal();

                        Action moveUp = getMoveUpAction();
                        moveUp.setDisabled(isFirst());

                        Action moveDown = getMoveDownAction();
                        moveDown.setDisabled(isLast());
                    }
                }
            });
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
}
