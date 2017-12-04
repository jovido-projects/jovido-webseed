package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class DatePayloadAttribute extends ValuePayloadAttribute<DatePayload> {

    @Override
    public DatePayload createPayload() {
        return new DatePayload();
    }
}
