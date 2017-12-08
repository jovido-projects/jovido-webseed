package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.FragmentService;
import biz.jovido.seed.content.Payload;
import biz.jovido.seed.content.PayloadAttribute;
import biz.jovido.seed.content.PayloadList;
import biz.jovido.seed.ui.Field;

/**
 * @author Stephan Grundner
 */
public class PayloadFieldFactory {

    private final FragmentService fragmentService;

    public <T> Field<T> createField(Payload<T> payload) {
        PayloadList<? extends Payload<T>> list = payload.getList();
        PayloadAttribute<T> attribute = fragmentService.getAttribute(list);
        FragmentSource.PayloadProperty<T> property = new FragmentSource.PayloadProperty<>(list, attribute.getCapacity());
        Field<T> field = attribute.createField();

        return field;
    }

    public PayloadFieldFactory(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }
}
