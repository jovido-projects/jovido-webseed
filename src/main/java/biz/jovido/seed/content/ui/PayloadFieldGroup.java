package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.Payload;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class PayloadFieldGroup implements HasTemplate {

    private final FragmentForm form;
    private final String attributeName;
    private String template = "admin/fragment/field-group";

    private final Map<Payload, PayloadField> fieldByPayload = new IdentityHashMap<>();

    public FragmentForm getForm() {
        return form;
    }

    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<PayloadField> getFields() {
        List<PayloadField> fields = fieldByPayload.values().stream()
                .sorted(Comparator.comparingInt(PayloadField::getOrdinal))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(fields);
    }

    protected void payloadAdded(Payload payload) {
        PayloadField field = fieldByPayload.get(payload);
        if (field == null) {
            field = new PayloadField(this);
            field.setTemplate("admin/fragment/field/text");
            field.setPayload(payload);
            fieldByPayload.put(payload, field);
        }
    }

    public PayloadFieldGroup(FragmentForm form, String attributeName) {
        this.form = form;
        this.attributeName = attributeName;
    }
}
