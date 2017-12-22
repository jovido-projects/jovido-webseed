package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class PayloadRemoved extends FragmentChange {

    private final Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public PayloadRemoved(Fragment fragment, Payload payload) {
        super(fragment);
        this.payload = payload;
    }
}
