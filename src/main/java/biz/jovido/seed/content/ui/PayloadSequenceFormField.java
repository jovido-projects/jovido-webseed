package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.FragmentService;
import biz.jovido.seed.content.Payload;
import biz.jovido.seed.content.PayloadSequence;
import biz.jovido.seed.content.structure.Attribute;
import biz.jovido.seed.content.structure.FragmentAttribute;
import biz.jovido.seed.content.structure.TextAttribute;
import biz.jovido.seed.ui.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Deprecated
public class PayloadSequenceFormField extends FormField {

    private final FragmentForm form;
    private final String attributeName;
    private Actions actions;

    private final Map<Payload, PayloadField> fields = new IdentityHashMap<>();

    public String getAttributeName() {
        return attributeName;
    }

    public PayloadSequence getSequence() {
        return form.getFragment().getSequence(attributeName);
    }

    public Actions getActions() {
        if (actions == null) {
            actions = new Actions();
        }

        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    private void updateActions(PayloadField... fields) {
        for (PayloadField field : fields) {
            Action moveUp = field.getMoveUpAction();
            moveUp.setUrl("/admin/fragment/move-up?field=" + field.getId());
            moveUp.setText(new StaticText("Move Up"));
            moveUp.setDisabled(field.isFirst());

            Action moveDown = field.getMoveDownAction();
            moveDown.setUrl("/admin/fragment/move-down?field=" + field.getId());
            moveDown.setText(new StaticText("Move Down"));
            moveDown.setDisabled(field.isLast());

            Action remove = field.getRemoveAction();
            remove.setUrl("/admin/fragment/remove?field=" + field.getId());
            remove.setText(new StaticText("Remove"));
        }
    }

    protected void payloadAdded(Payload payload) {
        final PayloadField field = new PayloadField();
        field.setPayload(payload);

        FragmentService fragmentService = form.fragmentService;
        Attribute attribute = fragmentService.getAttribute(payload);

        if (attribute instanceof TextAttribute) {
            field.setTemplate("admin/fragment/text-field");
        } else if (attribute instanceof FragmentAttribute) {
            field.setTemplate("admin/fragment/nested-fragment-field");
        } else {
            field.setTemplate("admin/fragment/payload-field");
        }

        if (attribute instanceof FragmentAttribute) {
            FragmentForm nestedForm = new FragmentForm(form.fragmentService);
            nestedForm.setNestedBindingPathProvider(new BindingPathProvider() {
                @Override
                public String getBindingPath() {
                    return String.format("%s.nestedForm", field.getBindingPath());
                }
            });
//            nestedForm.setFieldFactory(form.getFieldFactory());
            nestedForm.setFragment(payload.getFragment());
            field.setNestedForm(nestedForm);
        }

        field.setMoveUpAction(new Action());
        field.setMoveDownAction(new Action());
        field.setRemoveAction(new Action());

//        field.setBindingPathProvider(new BindingPathProvider() {
//            @Override
//            public String getBindingPath() {
//                return String.format("%s.fields[%d]",
//                        PayloadSequenceFormField.this.getBindingPath(),
//                        payload.getOrdinal());
//            }
//        });

        updateActions(field);

        fields.put(payload, field);
    }

    protected void payloadRemoved(Payload payload) {
        PayloadField field = fields.remove(payload);
        if (field != null) {
            updateActions(field);
        }
    }

    protected void payloadsSwapped(int i, int j) {
        Fragment fragment = form.getFragment();
        PayloadSequence sequence = fragment.getSequence(attributeName);
        List<Payload> payloads = sequence.getPayloads();
        PayloadField one = fields.get(payloads.get(i));
        PayloadField another = fields.get(payloads.get(j));
        if (one != null && another != null) {
            updateActions(one, another);
        }
    }

    public List<PayloadField> getFields() {
        List<PayloadField> list = fields.values().stream()
                .sorted(Comparator.comparingInt(it -> it.getPayload().getOrdinal()))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(list);
    }

    public Field findField(Predicate<Field> predicate) {
        for (PayloadField field : fields.values()) {
            if (predicate.test(field)) {
                return field;
            } else if (field != null) {
                FragmentForm nestedForm = field.getNestedForm();
                if (nestedForm != null) {
                    Field found = nestedForm.findField(predicate);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }

        return null;
    }

    @Deprecated
    public PayloadField findPayloadField(Predicate<PayloadField> predicate) {
        for (PayloadField field : fields.values()) {
            if (predicate.test(field)) {
                return field;
            }
        }

        return null;
    }

    public PayloadSequenceFormField(FragmentForm form, String attributeName) {
        this.form = form;
        this.attributeName = attributeName;

        setTemplate("admin/fragment/sequence-form-field");
    }
}
