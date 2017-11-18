package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("yesno")
public class YesNoPayload extends Payload {

    private boolean yes;

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }

    public YesNoPayload() {
        super(PayloadType.YES_NO);
    }
}
