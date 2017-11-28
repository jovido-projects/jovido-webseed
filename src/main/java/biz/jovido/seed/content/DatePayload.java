package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("date")
public class DatePayload extends Payload {

    private Date value;

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }

    @Override
    public Payload copy() {
        DatePayload copy = new DatePayload();
        copy.setValue(copy.getValue());

        return copy;
    }

    public DatePayload() {
        super(PayloadType.DATE);
    }
}
