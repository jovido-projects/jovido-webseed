package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.PayloadAttribute;
import biz.jovido.seed.content.PayloadList;
import biz.jovido.seed.content.PayloadValueListAdapter;
import biz.jovido.seed.ui.SourceProperty;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public class PayloadProperty implements SourceProperty {

    private FragmentSource source;
    private final String attributeName;
    private PayloadValueListAdapter<Object> values;

    @Override
    public String getName() {
        return attributeName;
    }

    @Override
    public List getValues() {
        return values;
    }

    @Override
    public Object getValue() {
        return values.get(0);
    }

    @Override
    public void setValue(Object value) {
        values.set(0, value);
    }

    private PayloadAttribute getAttribute() {
        return source.fragmentService.getAttribute(source.fragment, attributeName);
    }

    @Override
    public int getCapacity() {
        return getAttribute().getCapacity();
    }

    public PayloadProperty(FragmentSource source, String attributeName) {
        this.source = source;
        this.attributeName = attributeName;

        PayloadList list = source.fragment.getPayloadList(attributeName);
        values = new PayloadValueListAdapter<>(source.fragmentService, list);
    }
}
