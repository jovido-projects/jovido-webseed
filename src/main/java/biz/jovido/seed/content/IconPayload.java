package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("icon")
public class IconPayload extends Payload {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Payload copy() {
        IconPayload copy = new IconPayload();
        copy.setAttributeName(getAttributeName());
        copy.setCode(getCode());

        return copy;
    }

    public IconPayload() {
        super(PayloadType.ICON);
    }
}
