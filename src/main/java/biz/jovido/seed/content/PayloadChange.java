package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class PayloadChange {

    public static class PayloadAdded extends FragmentChange {

        private final String attributeName;
        private final int ordinal;

        public String getAttributeName() {
            return attributeName;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public PayloadAdded(Fragment fragment, String attributeName, int ordinal) {
            super(fragment);
            this.attributeName = attributeName;
            this.ordinal = ordinal;
        }
    }

    public static class PayloadRemoved extends FragmentChange {

        private final Payload payload;

        public Payload getPayload() {
            return payload;
        }

        public PayloadRemoved(Fragment fragment, Payload payload) {
            super(fragment);
            this.payload = payload;
        }
    }

    public static class PayloadsSwapped extends FragmentChange {

        private final String attributeName;
        private final int i;
        private final int j;

        public String getAttributeName() {
            return attributeName;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        public PayloadsSwapped(Fragment fragment, String attributeName, int i, int j) {
            super(fragment);
            this.attributeName = attributeName;
            this.i = i;
            this.j = j;
        }
    }

    private final Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public PayloadChange(Payload payload) {
        this.payload = payload;
    }

    /**
     * @author Stephan Grundner
     */
    public static class OrdinalSet extends PayloadChange {

        public OrdinalSet(Payload payload) {
            super(payload);
        }
    }
}
