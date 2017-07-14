package biz.jovido.seed.content;

import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
public class TextAttribute extends Attribute {

    private int rows = 1;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public boolean isMultiline() {
        return rows > 1;
    }

    @Override
    public Payload createPayload() {
        return new TextPayload();
    }

    public TextAttribute(Structure structure, String fieldName) {
        super(structure, fieldName);
    }

    public TextAttribute() {}
}
