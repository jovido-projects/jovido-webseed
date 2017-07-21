package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class Field {

    private final String propertyName;
    private Structure structure;

    private int ordinal;

    private int capacity = Integer.MAX_VALUE;
    private int required = 1;

    public String getPropertyName() {
        return propertyName;
    }

    public Structure getStructure() {
        return structure;
    }

    /* public */ void setStructure(Structure structure) {
        this.structure = structure;
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

    protected abstract Payload createPayload();

    public Field(String propertyName) {
        this.propertyName = propertyName;
    }
}
