package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public class NodeForm {

    public static class ReferringValue {

        private Long propertyId;
        private int valueIndex;

        public Long getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(Long propertyId) {
            this.propertyId = propertyId;
        }

        public int getValueIndex() {
            return valueIndex;
        }

        public void setValueIndex(int valueIndex) {
            this.valueIndex = valueIndex;
        }
    }

    private Locale locale;
    private Node node;
    private final List<ReferringValue> referringValues = new ArrayList<>();

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public List<ReferringValue> getReferringValues() {
        return referringValues;
    }

    public boolean addReferringValue(ReferringValue referringValue) {
        return referringValues.add(referringValue);
    }

    public Fragment getFragment() {
        if (node == null) {
            return null;
        }

        return node.getFragment(locale);
    }
}
