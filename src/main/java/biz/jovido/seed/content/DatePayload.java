package biz.jovido.seed.content;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("date")
public class DatePayload extends Payload<Date> {

    @Column(name = "date_value")
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
