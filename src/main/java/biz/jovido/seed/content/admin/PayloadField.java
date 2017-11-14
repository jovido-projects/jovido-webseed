package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.Payload;

/**
 * @author Stephan Grundner
 */
public class PayloadField {

    private final PayloadFieldGroup fieldGroup;
    private int ordinal;

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public Payload getPayload() {
        return fieldGroup.getPayloadGroup().getPayload(ordinal);
    }

    public PayloadField(PayloadFieldGroup fieldGroup, int ordinal) {
        this.fieldGroup = fieldGroup;

        setOrdinal(ordinal);
    }
}
