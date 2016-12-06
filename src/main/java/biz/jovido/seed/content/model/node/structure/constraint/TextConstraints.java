package biz.jovido.seed.content.model.node.structure.constraint;

import biz.jovido.seed.content.model.node.fragment.property.TextProperty;
import biz.jovido.seed.content.model.node.structure.Constraints;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
//@Table(name = "alphanumeric_constraint")
@DiscriminatorValue("text")
@Entity
public class TextConstraints extends Constraints {

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

    @Deprecated
    public TextConstraints() {
        super(TextProperty.class);
    }
}
