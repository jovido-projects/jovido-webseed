package biz.jovido.seed.content;

import java.util.Date;

/**
 * @author Stephan Grundner
 */
public class DatePayloadAttribute extends PayloadAttribute<Date> {

    @Override
    public Payload<Date> createPayload() {
        return new DatePayload();
    }
}
