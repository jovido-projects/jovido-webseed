package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.Field;
import biz.jovido.seed.ui.Source;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class FragmentForm {

    private final FragmentService fragmentService;

    private Fragment fragment;
    private String nestedPath;

    private final Map<String, Field<?>> fieldByAttributeName = new HashMap<>();

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;

        FragmentSource source = new FragmentSource(fragmentService, fragment);

        FragmentStructure structure = fragmentService.getStructure(fragment);
        for (String attributeName : structure.getAttributeNames()) {
            PayloadAttribute<?> attribute = structure.getAttribute(attributeName);
            Field field = attribute.createField();
            fieldByAttributeName.put(attributeName, field);
            String bindingPath = String.format("%s.fields[%s]", nestedPath, attributeName);
            field.setBindingPath(bindingPath);

            Source.Property<?> property = source.getProperties().get(attributeName);
            field.setProperty(property);
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

    public FragmentForm(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }
}
