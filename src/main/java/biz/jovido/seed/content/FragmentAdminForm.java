package biz.jovido.seed.content;

import biz.jovido.seed.Form;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
public class FragmentAdminForm implements Form {

    public static class Origin {

        private FragmentAdminForm form;
        private String attributeName;
        private int valueIndex;

        public FragmentAdminForm getForm() {
            return form;
        }

        public void setForm(FragmentAdminForm form) {
            this.form = form;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public int getValueIndex() {
            return valueIndex;
        }

        public void setValueIndex(int valueIndex) {
            this.valueIndex = valueIndex;
        }
    }

    private final String id = UUID.randomUUID().toString();

    private Fragment fragment;
    private Origin origin;

    public String getId() {
        return id;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public String getTitle() {
        if (fragment != null) {
            String typeName = fragment.getBundle().getTypeName();
            Long fragmentId = fragment.getId();
            if (fragmentId != null) {
                return String.format("%s:%d", typeName, fragmentId);
            } else {
                return String.format("new:%s", typeName);
            }
        }
        return null;
    }
}
