package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class PayloadAdded extends FragmentChange {

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
