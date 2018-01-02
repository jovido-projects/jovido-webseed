package biz.jovido.seed.content;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.ui.BindingPathProvider;
import biz.jovido.seed.ui.Field;
import biz.jovido.seed.ui.FixedBindingPathProvider;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class FragmentForm implements HasTemplate, FragmentChangeListener {

    public interface PayloadSequenceFieldFactory {

        PayloadSequenceField createPayloadSequenceField(FragmentForm form, PayloadSequence sequence);
    }

    protected final FragmentService fragmentService;

    private Fragment fragment;
    private String template = "admin/fragment/form";
    private BindingPathProvider nestedBindingPathProvider;

    private PayloadSequenceFieldFactory payloadSequenceFieldFactory;

    private final Set<PayloadSequenceField> payloadSequenceFields = new HashSet<>();

    protected PayloadSequenceField getField(PayloadSequence sequence) {
        return payloadSequenceFields.stream()
                .filter(it -> Objects.equals(it.getSequence(), sequence))
                .findFirst().orElse(null);
    }

    private PayloadSequenceField getOrCreateField(PayloadSequence sequence) {
        PayloadSequenceField field = getField(sequence);
        if (field == null) {
            field = payloadSequenceFieldFactory.createPayloadSequenceField(this, sequence);
//            field.setSequence(sequence);
            payloadSequenceFields.add(field);
        }

        return field;
    }

    @Override
    public void fragmentChanged(FragmentChange change) {
        if (change instanceof PayloadChange.PayloadsSwapped) {
            PayloadChange.PayloadsSwapped payloadsSwapped = (PayloadChange.PayloadsSwapped) change;

        } else if (change instanceof PayloadChange.PayloadAdded) {
            PayloadChange.PayloadAdded payloadAdded = (PayloadChange.PayloadAdded) change;
            PayloadSequence sequence = fragment.getSequence(payloadAdded.getAttributeName());
            Payload added = sequence.getPayloads().get(payloadAdded.getOrdinal());
            PayloadSequenceField field = getOrCreateField(sequence);
            field.addFieldFor(added);

        } else if (change instanceof PayloadChange.PayloadRemoved) {
            PayloadChange.PayloadRemoved payloadRemoved = (PayloadChange.PayloadRemoved) change;
            Payload removed = payloadRemoved.getPayload();
            PayloadSequence sequence = removed.getSequence();
            PayloadSequenceField field = getField(sequence);
            if (field != null) {
                field.removeFieldFor(removed);
            }

        } else {
            throw new RuntimeException("Unexpected event type " + change.getClass());
        }
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        Fragment previous = this.fragment;
        if (previous != null) {
            previous.removeChangeListener(this);
        }

        this.fragment = fragment;

        clear();

        if (fragment != null) {
            fragment.addChangeListener(this);

            for (Payload payload : fragment.getAllPayloads()) {
                PayloadSequence sequence = payload.getSequence();
                PayloadSequenceField field = getOrCreateField(sequence);
                field.addFieldFor(payload);
            }
        }
    }

    public void clear() {
        payloadSequenceFields.clear();
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public BindingPathProvider getNestedBindingPathProvider() {
        return nestedBindingPathProvider;
    }

    public void setNestedBindingPathProvider(BindingPathProvider nestedBindingPathProvider) {
        this.nestedBindingPathProvider = nestedBindingPathProvider;
    }

    public String getNestedBindingPath() {
        return nestedBindingPathProvider.getBindingPath();
    }

    public void setNestedBindingPath(String nestedBindingPath) {
        nestedBindingPathProvider = new FixedBindingPathProvider(nestedBindingPath);
    }

    public PayloadSequenceFieldFactory getPayloadSequenceFieldFactory() {
        return payloadSequenceFieldFactory;
    }

    public void setPayloadSequenceFieldFactory(PayloadSequenceFieldFactory payloadSequenceFieldFactory) {
        this.payloadSequenceFieldFactory = payloadSequenceFieldFactory;
    }

    public List<PayloadSequenceField> getFields() {
        List<PayloadSequenceField> results = payloadSequenceFields.stream()
                .sorted(Comparator.comparingInt(PayloadSequenceField::getOrdinal))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(results);
    }

    public Field findField(Predicate<Field> predicate) {
        for (PayloadSequenceField field : payloadSequenceFields) {
            if (predicate.test(field)) {
                return field;
            }

            if (field != null) {
                Field found = field.findField(predicate);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    public FragmentForm(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }
}
