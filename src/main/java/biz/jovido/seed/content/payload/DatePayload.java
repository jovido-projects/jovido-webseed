package biz.jovido.seed.content.payload;

import biz.jovido.seed.content.Payload;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Stephan Grundner
 */
@Entity
public class DatePayload extends Payload<Date> {

    private Date value;

    @Override
    public Date getValue() {
        return value;
    }

    @Override
    public void setValue(Date value) {
        this.value = value;
    }
}
