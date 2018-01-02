package biz.jovido.seed.content;

import biz.jovido.seed.ui.Actions;
import biz.jovido.seed.ui.Field;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class PayloadSequenceField extends Field {

    public interface PayloadFieldFactory {

        PayloadField createPayloadField(FragmentForm form, Payload payload);
    }

    private final FragmentForm form;
    private final PayloadSequence sequence;

    private PayloadFieldFactory payloadFieldFactory;

    private final Set<PayloadField> payloadFields = new HashSet<>();

    private Actions appendActions;

    public PayloadSequence getSequence() {
        return sequence;
    }

    private Attribute getAttribute() {
        FragmentService fragmentService = form.fragmentService;
        return fragmentService.getAttribute(sequence);
    }

    public int getOrdinal() {
        return getAttribute().getOrdinal();
    }

    public PayloadFieldFactory getPayloadFieldFactory() {
        return payloadFieldFactory;
    }

    public void setPayloadFieldFactory(PayloadFieldFactory payloadFieldFactory) {
        this.payloadFieldFactory = payloadFieldFactory;
    }

    public Set<PayloadField> getPayloadFields() {
        return payloadFields;
    }

    protected void addFieldFor(Payload payload) {
        PayloadField field = getPayloadFieldFactory().createPayloadField(form, payload);
        payloadFields.add(field);
        for (PayloadField f : getFields()) {
            f.updateActions();
        }
    }

    protected void removeFieldFor(Payload payload) {
        if (payloadFields.removeIf(it -> Objects.equals(payload, it.getPayload()))) {
            for (PayloadField field : getFields()) {
                field.updateActions();
            }
        }
    }

    public List<PayloadField> getFields() {
        return payloadFields.stream()
                .sorted(Comparator.comparingInt(PayloadField::getOrdinal))
                .collect(Collectors.toList());
    }

    public Field findField(Predicate<Field> predicate) {
        for (PayloadField field : payloadFields) {
            if (predicate.test(field)) {
                return field;
            }

            FragmentForm nestedForm = field.getNestedForm();
            if (nestedForm != null) {
                Field found = nestedForm.findField(predicate);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    public Actions getAppendActions() {
        return appendActions;
    }

    public void setAppendActions(Actions appendActions) {
        this.appendActions = appendActions;
    }

    public PayloadSequenceField(FragmentForm form, PayloadSequence sequence) {
        this.form = form;
        this.sequence = sequence;
    }
}
