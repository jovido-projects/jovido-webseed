package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class Attribute {

    private Structure structure;

    private final String fieldName;

    private int ordinal;

    private int capacity = 1;
    private int required = 1;

    public Structure getStructure() {
        return structure;
    }

    /* public */ void setStructure(Structure structure) {
        this.structure = structure;
    }

    public String getFieldName() {
        return fieldName;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public abstract Payload createPayload();

    @Deprecated
    public Attribute(String fieldName) {
        this.fieldName = fieldName;
    }

    public Attribute(Structure structure, String fieldName) {
        this.fieldName = fieldName;

        structure.putAttribute(this);
    }
}
