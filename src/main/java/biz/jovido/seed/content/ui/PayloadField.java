package biz.jovido.seed.content.ui;

import biz.jovido.seed.ui.Field;
import biz.jovido.seed.ui.SourceProperty;

/**
 * @author Stephan Grundner
 */
public class PayloadField<T> extends Field<T> {

    public PayloadField(SourceProperty<T> property) {
        super(property);
    }
}
