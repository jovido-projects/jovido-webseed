package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Payload;
import biz.jovido.seed.ui.Field;

/**
 * @author Stephan Grundner
 */
public class PayloadField extends Field {

    private final PayloadFieldGroup fieldList;
    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;

//        setValueAccessor(new ValueAccessor() {
//            @Override
//            public Object getValue() {
//                return payload.;
//            }
//
//            @Override
//            public void setValue(Object value) {
//
//            }
//        });
    }

    public int getOrdinal() {
        return getPayload().getOrdinal();
    }

    public PayloadField(PayloadFieldGroup fieldList) {
        this.fieldList = fieldList;
    }
}
