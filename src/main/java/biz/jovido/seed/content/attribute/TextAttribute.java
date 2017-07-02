package biz.jovido.seed.content.attribute;

import biz.jovido.seed.content.Attribute;
import biz.jovido.seed.content.payload.TextPayload;

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
    public TextPayload createPayload() {
        return new TextPayload();
    }

    public TextAttribute(String name) {
        super(name);
    }

    public TextAttribute() {}
}
