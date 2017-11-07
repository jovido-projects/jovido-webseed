package biz.jovido.seed.mvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class ActionGroup extends Text {

    private final List<Action> actions = new ArrayList<>();
    private Action defaultAction;

    public List<Action> getActions() {
        return Collections.unmodifiableList(actions);
    }

    public boolean addAction(Action action) {
        if (actions.add(action)) {
            return true;
        }

        return false;
    }

    public Action getDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(Action defaultAction) {
        this.defaultAction = defaultAction;
    }
}
