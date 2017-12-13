package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.FragmentService;
import biz.jovido.seed.content.Payload;
import biz.jovido.seed.ui.Action;
import biz.jovido.seed.ui.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class PayloadField extends Field<PayloadFieldGroup> implements HasTemplate {

    private Payload payload;
    private String template;
    private final Map<String, Action> actions = new HashMap<>();
    private FragmentForm nestedForm;
    private String bindingPath;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Action> getActions() {
        return actions;
    }

    public FragmentForm getNestedForm() {
        return nestedForm;
    }

    public void setNestedForm(FragmentForm nestedForm) {
        this.nestedForm = nestedForm;
    }

    public int getOrdinal() {
        return getPayload().getOrdinal();
    }

    @Override
    public String getBindingPath() {
        return bindingPath;
    }

    public void setBindingPath(String bindingPath) {
        this.bindingPath = bindingPath;
    }

    public void invalidate() {
        Map<String, Action> actions = getActions();
        actions.clear();

        Action moveUp = new Action();
        moveUp.setUrl("/admin/fragment/move-up?field=" + getId());
        moveUp.setDisabled(payload.getOrdinal() == 0);
        actions.put("moveUp", moveUp);

        Action moveDown = new Action();
        moveDown.setUrl("/admin/fragment/move-up?");
        moveDown.setDisabled(payload.getOrdinal() == payload.getSequence().getPayloads().size() - 1);
        actions.put("moveDown", moveDown);
    }

    public PayloadField(PayloadFieldGroup group) {
        setGroup(group);
    }
}
