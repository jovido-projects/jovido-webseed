package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.*;
import biz.jovido.seed.content.structure.Structure;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class FragmentForm implements HasTemplate {

    private final FragmentService fragmentService;

    private final Map<String, PayloadFieldGroup> fieldGroupByAttributeName = new IdentityHashMap<>();

    private Fragment fragment;
    private String template = "admin/fragment/form";
    private String nestedBindingPath;

    public Map<String, PayloadFieldGroup> getFieldGroups() {
        return Collections.unmodifiableMap(fieldGroupByAttributeName);
    }

    public Fragment getFragment() {
        return fragment;
    }

    private void payloadsAdded(List<Payload> payloads) {
        for (Payload payload : payloads) {
            PayloadSequence payloadList = payload.getSequence();
            String attributeName = payloadList.getAttributeName();
            PayloadFieldGroup fieldList = fieldGroupByAttributeName.get(attributeName);
            if (fieldList == null) {
                fieldList = new PayloadFieldGroup(this, attributeName);
                fieldGroupByAttributeName.put(attributeName, fieldList);
            }

            fieldList.payloadAdded(payload);
        }
    }

    private void fragmentChanged(FragmentChange change) {
        payloadsAdded(change.getPayloadsAdded());
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;

        fieldGroupByAttributeName.clear();

        if (fragment != null) {
            Structure structure = fragmentService.getStructure(fragment);
            for (String attributeName : structure.getAttributeNames()) {
                PayloadSequence sequence = fragment.getSequence(attributeName);
                payloadsAdded(sequence.getPayloads());
            }

            fragment.addChangeListener(new FragmentChangeListener() {
                @Override
                public void fragmentChanged(FragmentChange change) {
                    FragmentForm.this.fragmentChanged(change);
                }
            });
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

    public FragmentForm(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }
}
