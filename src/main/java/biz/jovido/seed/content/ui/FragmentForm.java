package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.Field;
import biz.jovido.seed.ui.SourceProperty;
import biz.jovido.seed.ui.TextField;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class FragmentForm implements HasTemplate {

    private final FragmentService fragmentService;

    private Fragment fragment;
    private String nestedPath;

    private String template = "admin/fragment/form";

    private final Map<String, Field<?>> fieldByAttributeName = new HashMap<>();

    public Fragment getFragment() {
        return fragment;
    }

    private Field<?> createField(String attributeName) {
        FragmentStructure structure = fragmentService.getStructure(fragment);
        PayloadAttribute attribute = structure.getAttribute(attributeName);

        String bindingPath = String.format("%s.fields[%s]", nestedPath, attributeName);

        Field<?> field;

        if (attribute instanceof TextPayloadAttribute) {
            field = new TextField();

        } else if (attribute instanceof FragmentPayloadAttribute) {
            field = new FragmentFormField();
            FragmentForm nestedForm = new FragmentForm(fragmentService);
            nestedForm.setNestedPath(bindingPath + ".form");
            ((FragmentFormField) field).setForm(nestedForm);
        } else {
            throw new RuntimeException();
        }

        field.setBindingPath(bindingPath);

        return field;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;

        if (fragment != null) {
            FragmentSource source = new FragmentSource(fragmentService, fragment);

            FragmentStructure structure = fragmentService.getStructure(fragment);
            for (String attributeName : structure.getAttributeNames()) {

                Field<?> field = fieldByAttributeName.get(attributeName);
                if (field == null) {
                    field = createField(attributeName);
                    fieldByAttributeName.put(attributeName, field);
                }

                SourceProperty property = source.getProperty(attributeName);
                field.setProperty(property);
            }
        }
    }

    public String getNestedPath() {
        return nestedPath;
    }

    public void setNestedPath(String nestedPath) {
        this.nestedPath = nestedPath;
    }

    public Map<String, Field<?>> getFields() {
        return Collections.unmodifiableMap(fieldByAttributeName);
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public FragmentForm(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }
}
