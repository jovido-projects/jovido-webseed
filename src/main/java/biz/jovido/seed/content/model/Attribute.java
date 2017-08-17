package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public abstract class Attribute {

    private Structure structure;

    private String name;
    private int capacity = Integer.MAX_VALUE;
    private int required = 1;

    private int ordinal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public abstract Payload createPayload();
}
