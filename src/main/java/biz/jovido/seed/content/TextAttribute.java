package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
//@Entity
public class TextAttribute extends SimpleAttribute {

    private int rows = 1;

    private int maxLength = Integer.MAX_VALUE;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
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
}
