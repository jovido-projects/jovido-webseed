package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.FragmentService;
import biz.jovido.seed.content.PayloadSequence;
import biz.jovido.seed.content.structure.Structure;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class FragmentForm implements HasTemplate {

    protected final FragmentService fragmentService;

    private Fragment fragment;
    private String template = "admin/fragment/form";
    private String nestedBindingPath;

    private Map<String, PayloadFieldGroup> fieldGroupByAttributeName = new HashMap<>();

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;

        fieldGroupByAttributeName.clear();

        if (fragment != null) {
            Structure structure = fragmentService.getStructure(fragment);

            int i = 0;
            for (String attributeName : structure.getAttributeNames()) {
                PayloadSequence sequence = fragment.getSequence(attributeName);
                PayloadFieldGroup fieldGroup = fieldGroupByAttributeName.get(attributeName);
                if (fieldGroup == null) {
                    fieldGroup = new PayloadFieldGroup(this);
                    String bindingPath = String.format("%s.fieldGroups[%d]", nestedBindingPath, i);
                    fieldGroup.setNestedBindingPath(bindingPath);
                    fieldGroup.setOrdinal(i++);
                    fieldGroup.setTemplate("admin/fragment/field-group");
                    fieldGroup.setSequence(sequence);
                    fieldGroupByAttributeName.put(attributeName, fieldGroup);
                }
            }
        }
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getNestedBindingPath() {
        return nestedBindingPath;
    }

    public void setNestedBindingPath(String nestedBindingPath) {
        this.nestedBindingPath = nestedBindingPath;
    }

    public List<PayloadFieldGroup> getFieldGroups() {
        List<PayloadFieldGroup> fieldGroups = fieldGroupByAttributeName.values().stream()
                .sorted(Comparator.comparingInt(PayloadFieldGroup::getOrdinal)).distinct()
                .collect(Collectors.toList());

        return Collections.unmodifiableList(fieldGroups);
    }

    public PayloadField findField(Predicate<PayloadField> predicate) {
        for (PayloadFieldGroup fieldGroup : getFieldGroups()) {
            PayloadField field = fieldGroup.findField(predicate);
            if (field != null) {
                return field;
            }
        }

        return null;
    }

    public FragmentForm(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }
}
