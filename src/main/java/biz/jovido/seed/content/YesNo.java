package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("YesNo")
public class YesNo extends Payload {

    public Boolean getValue() {
        return Boolean.parseBoolean(getText());
    }

    public void setValue(Boolean value) {
        setText(value.toString());
    }

    @Override
    public Payload copy() {
        YesNo copy = new YesNo();
        copy.setValue(getValue());

        return copy;
    }
}
