package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.ui.BindingProperty;
import biz.jovido.seed.ui.Field;

/**
 * @author Stephan Grundner
 */
public class FragmentFormField extends Field<Fragment> implements HasTemplate {

    private String template = "admin/fragment/field";
    private FragmentForm form;

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public FragmentForm getForm() {
        return form;
    }

    public void setForm(FragmentForm form) {
        this.form = form;
    }

    @Override
    public void setProperty(BindingProperty<Fragment> property) {
        super.setProperty(property);
        Fragment fragment = property.getValue();
        form.setFragment(fragment);
    }

    public FragmentFormField() {}
}
