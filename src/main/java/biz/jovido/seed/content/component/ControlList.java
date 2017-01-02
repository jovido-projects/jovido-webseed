package biz.jovido.seed.content.component;

import biz.jovido.spring.web.ui.MvcComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@MvcComponent("content/component/control-list")
public class ControlList extends Control {

    private final List<Control> controls = new ArrayList<>();

    public List<Control> getControls() {
        return Collections.unmodifiableList(controls);
    }

    public boolean addControl(Control control) {
        return controls.add(control);
    }

    public boolean removeControl(Control control) {
        return controls.remove(control);
    }

    public ControlList(String nestedPath) {
        super(nestedPath);
    }
}
