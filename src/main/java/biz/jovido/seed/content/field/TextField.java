package biz.jovido.seed.content.field;

import biz.jovido.seed.content.Field;

/**
 * @author Stephan Grundner
 */
public class TextField extends Field {

    private int rows = 1;

    public boolean isMultiline() {
        return rows > 1;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public TextField(String name) {
        super(name);
    }
}
