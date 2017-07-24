package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class Attribute {

    private final String name;
    private int ordinal;
    private Structure structure;

    public String getName() {
        return name;
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

    public Attribute(String name) {
        this.name = name;
    }
}
