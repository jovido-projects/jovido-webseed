package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.FieldGroup;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class PayloadFieldGroup implements FieldGroup<PayloadField, PayloadFieldGroup> {

    private final FragmentForm form;

    private String id;
    private PayloadSequence sequence;
    private int ordinal;
    private String template;
    private String nestedBindingPath;

    private PayloadFieldGroup parent;
    private final Set<PayloadFieldGroup> children = new LinkedHashSet<>();

    private final Set<PayloadField> fields = new LinkedHashSet<>();

    @Override
    public String getId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }

        return id;
    }

    protected FragmentService getFragmentService() {
        return form.fragmentService;
    }

    public PayloadSequence getSequence() {
        return sequence;
    }

    public void setSequence(PayloadSequence sequence) {
        this.sequence = sequence;

        for (Payload payload : sequence.getPayloads()) {
            payloadAdded(payload);
        }

        Fragment fragment = sequence.getFragment();
        fragment.addChangeListener(new FragmentChangeListener() {
            @Override
            public void fragmentChanged(FragmentChange change) {
                for (Payload payload : change.getPayloadsAdded()) {
                    if (payload.getSequence() == getSequence()) {
                        payloadAdded(payload);
                    }
                }
//                invalidate();
            }
        });
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public Collection<PayloadFieldGroup> getNestedFieldGroups() {
        return children;
    }

    @Override
    public Collection<PayloadField> getFields() {
        return fields.stream()
                .sorted(Comparator.comparingInt(PayloadField::getOrdinal))
                .collect(Collectors.toList());
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

    public void setParent(PayloadFieldGroup parent) {
        this.parent = parent;
    }

    private void payloadAdded(Payload payload) {
        PayloadField field = new PayloadField(this);
        field.setPayload(payload);
        field.setTemplate("admin/fragment/field/text");
        fields.add(field);
        invalidate(field);
    }

    private void invalidate(PayloadField field) {
        String bindingPath = String.format("%s.fields[%d]", nestedBindingPath, field.getOrdinal());
        field.setBindingPath(bindingPath);
        field.invalidate();
    }

    public void invalidate() {
        for (PayloadField field : getFields()) {
            invalidate(field);
        }
    }

    public PayloadField findField(Predicate<PayloadField> predicate) {
        for (PayloadField field : getFields()) {
            if (predicate.test(field)) {
                return field;
            } else {

                FragmentForm nestedForm = field.getNestedForm();
                if (nestedForm != null) {
                    nestedForm.findField(predicate);
                }
            }
        }

        return null;
    }

    public PayloadFieldGroup(FragmentForm form) {
        this.form = form;
    }
}
