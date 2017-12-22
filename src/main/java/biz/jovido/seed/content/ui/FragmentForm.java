package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.BindingPathProvider;
import biz.jovido.seed.ui.FixedBindingPathProvider;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class FragmentForm implements HasTemplate, FragmentChangeListener {

    public interface PayloadFieldFactory {

        PayloadField createPayloadField(FragmentForm form, Payload payload);
    }

    protected final FragmentService fragmentService;

    private Fragment fragment;
    private String template = "admin/fragment/form";
    private BindingPathProvider nestedBindingPathProvider;

    private PayloadFieldFactory fieldFactory;

    private Map<String, PayloadField> fields = new HashMap<>();

    private void addFieldFor(Payload payload) {
        PayloadField field = fieldFactory.createPayloadField(this, payload);
        fields.put(field.getId(), field);
    }


    @Override
    public void fragmentChanged(FragmentChange change) {
        if (change instanceof PayloadsSwapped) {
            PayloadsSwapped payloadsSwapped = (PayloadsSwapped) change;

        } else if (change instanceof PayloadAdded) {
            PayloadAdded payloadAdded = (PayloadAdded) change;
            PayloadSequence sequence = fragment.getSequence(payloadAdded.getAttributeName());
            Payload payload = sequence.getPayloads().get(payloadAdded.getOrdinal());
            addFieldFor(payload);

        } else if (change instanceof PayloadRemoved) {
            PayloadRemoved payloadRemoved = (PayloadRemoved) change;
            PayloadField field = findField(it -> it.getPayload() == payloadRemoved.getPayload());
            if (field != null) {
                fields.remove(field.getId());
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
                addFieldFor(payload);
            }
        }
    }

    public void clear() {
        fields.clear();
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

    public PayloadFieldFactory getFieldFactory() {
        return fieldFactory;
    }

    public void setFieldFactory(PayloadFieldFactory fieldFactory) {
        this.fieldFactory = fieldFactory;
    }

    public Map<String, PayloadField> getFieldsById() {
        return Collections.unmodifiableMap(fields);
    }

    public Map<String, List<PayloadField>> getFieldsByAttributeName() {
        Collection<PayloadField> fields = getFieldsById().values();
        Map<String, List<PayloadField>> x = fields.stream()
                .filter(it -> it.getPayload() != null)
                .collect(Collectors.groupingBy(
                        it -> {
                            Payload payload = it.getPayload();
                            if (payload != null) {
                                PayloadSequence sequence = payload.getSequence();
                                if (sequence != null) {
                                    return sequence.getAttributeName();
                                }
                            }

                            return null;
                        },
                        Collectors.collectingAndThen(Collectors.toList(), l -> {
                            return l.stream()
                                    .sorted(Comparator.comparingInt(it -> it.getPayload().getOrdinal()))
                                    .collect(Collectors.toList());
                        })));

        return x;
    }

    public PayloadField findField(Predicate<PayloadField> predicate) {
        for (PayloadField field : fields.values()) {
            if (predicate.test(field)) {
                return field;
            }

            FragmentForm nestedForm = field.getNestedForm();
            if (nestedForm != null) {
                PayloadField found = nestedForm.findField(predicate);
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
