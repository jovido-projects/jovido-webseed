package biz.jovido.seed.content.model.node.field.constraint;

import biz.jovido.seed.content.model.node.field.Constraint;
import biz.jovido.seed.content.model.node.fragment.payload.TextPayload;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Stephan Grundner
 */
@Table(name = "alphanumeric_constraint")
@DiscriminatorValue("alphanumeric")
@Entity
public class AlphanumericConstraint extends Constraint {

    private boolean multiline;
    private boolean html;

    public boolean getMultiline() {
        return multiline;
    }

    public boolean isMultiline() {
        return multiline;
    }

    public void setMultiline(boolean multiline) {
        this.multiline = multiline;
    }

    public boolean getHtml() {
        return html;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public AlphanumericConstraint() {
        addSupportedPayloadType(TextPayload.class);
    }
}
