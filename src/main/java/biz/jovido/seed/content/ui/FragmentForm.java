package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class FragmentForm implements Form, HasTemplate {

    private final FragmentService fragmentService;

    private String nestedPath;
    private FragmentBinding binding;

    private String template = "admin/fragment/form";

    private final Map<String, Field<?>> fieldByAttributeName = new HashMap<>();

    private Field<?> createField(String attributeName) {
        Fragment fragment = binding.fragment;
        FragmentStructure structure = fragmentService.getStructure(fragment);
        PayloadAttribute attribute = structure.getAttribute(attributeName);
        String bindingPath = String.format("%s.fields[%s]", nestedPath, attributeName);

        Field field;

        if (attribute instanceof FragmentPayloadAttribute) {
            field = new FormField<>();
            field.setTemplate("admin/fragment/field");
            FragmentForm nestedForm = new FragmentForm(fragmentService);
            nestedForm.setNestedPath(bindingPath + ".form");
            ((FormField<?>) field).setForm(nestedForm);
        } else {

            field = new Field<>();
            field.setTemplate("ui/field/text");

            if (attribute instanceof TextPayloadAttribute) {

                if (((TextPayloadAttribute) attribute).isMultiline()) {
                    field.setTemplate("ui/field/multiline-text");
                }

            }
        }

        field.setBindingPath(bindingPath);
        field.addInvalidationListener(new InvalidationListener<Field<?>>() {
            @Override
            public void invalidate(Field<?> field) {

            }
        });

        return field;
    }

    public Fragment getFragment() {
        if (binding != null) {
            return binding.fragment;
        }

        return null;
    }

    public void setFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentBinding binding = new FragmentBinding(fragmentService, fragment);
            setBinding(binding);

            FragmentStructure structure = fragmentService.getStructure(fragment);
            for (String attributeName : structure.getAttributeNames()) {
                Field<?> field = fieldByAttributeName.get(attributeName);
                if (field == null) {
                    field = createField(attributeName);
                    fieldByAttributeName.put(attributeName, field);
                }

                FieldUtils.bind(field, binding, attributeName);
            }


        }
    }

    public String getNestedPath() {
        return nestedPath;
    }

    @Override
    public FragmentBinding getBinding() {
        return binding;
    }

    @Override
    public void setBinding(Binding binding) {
        this.binding = (FragmentBinding) binding;
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
