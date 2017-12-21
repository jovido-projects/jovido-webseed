package biz.jovido.seed.content.event;

import biz.jovido.seed.content.Fragment;

/**
 * @author Stephan Grundner
 */
public class PayloadsSwapped extends FragmentChange {

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
