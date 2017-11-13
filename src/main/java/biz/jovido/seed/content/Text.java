package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.util.Objects;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("Text")
public class Text extends Payload {

    public String getValue() {
        return getText();
    }

    public void setValue(String value) {
        setText(value);
    }

    @Override
    public boolean differsFrom(Payload other) {
        if (other == null) {
            return true;
        }

        return !Objects.equals(getValue(), ((Text) other).getValue());
    }

    @Override
    public Payload copy() {
        Text copy = new Text();
        copy.setValue(getValue());

        return copy;
    }
}
