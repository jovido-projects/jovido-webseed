package biz.jovido.seed.content.model.node.fragment.payload;

import biz.jovido.seed.content.model.node.fragment.Payload;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue(Payload.Type.DATE)
@Entity
public class DatePayload extends Payload<Date> {

    @Column(name = "date_value")
    private Date value;

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }
}
