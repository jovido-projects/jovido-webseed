package biz.jovido.seed.content.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
public class TextAttribute extends Attribute {

    @Column(name = "size")
    private int lines = 1;

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public boolean isMultiline() {
        return lines > 1;
    }

    @Override
    public Payload createPayload() {
        return new TextPayload();
    }
}
