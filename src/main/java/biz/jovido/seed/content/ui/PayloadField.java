package biz.jovido.seed.content.ui;

import biz.jovido.seed.ui.Field;
import biz.jovido.seed.ui.Source;

/**
 * @author Stephan Grundner
 */
public class PayloadField<T> extends Field<T> {

    public PayloadField(Source.Property<T> property) {
        super(property);
    }
}
