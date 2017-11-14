package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.Attribute;
import biz.jovido.seed.content.PayloadGroup;
import org.apache.commons.collections4.list.LazyList;
import org.apache.commons.collections4.map.LazyMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class PayloadFieldGroup {

    private final ItemEditor editor;
    private String attributeName;
    private final Map<Integer, PayloadField> fields = LazyMap.lazyMap(new HashMap<Integer, PayloadField>(),
            ordinal -> new PayloadField(PayloadFieldGroup.this, ordinal));

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;

        fields.clear();
    }

    public PayloadGroup getPayloadGroup() {
        return editor.getItem().getPayloadGroup(attributeName);
    }

    public Attribute getAttribute() {
        return editor.itemService.getAttribute(getPayloadGroup());
    }

    public List<PayloadField> getFields() {
        List<PayloadField> list = fields.values().stream()
                .sorted(Comparator.comparingInt(PayloadField::getOrdinal))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(list);
    }

    public PayloadFieldGroup(ItemEditor editor, String attributeName) {
        this.editor = editor;
        setAttributeName(attributeName);
    }
}
