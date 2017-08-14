package biz.jovido.seed.content.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("text")
public class TextValue extends Value {

    private String text;

    @Override
    public String getData() {
        return text;
    }

    @Override
    public void setData(Object data) {
        text = (String) data;
    }
}
