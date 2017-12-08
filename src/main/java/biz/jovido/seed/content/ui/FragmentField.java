package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.ui.Field;
import biz.jovido.seed.ui.Source;

/**
 * @author Stephan Grundner
 */
public class FragmentField extends Field<Fragment> implements HasTemplate {

    private String template = "admin/fragment/field";

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public FragmentField() {}
}
