package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class Field {

    private Structure structure;

    private final String name;
    private int capacity = Integer.MAX_VALUE;
    private int minimumNumberOfValues = 1;

    public Structure getStructure() {
        return structure;
    }

    void setStructure(Structure structure) {
        this.structure = structure;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMinimumNumberOfValues() {
        return minimumNumberOfValues;
    }

    public void setMinimumNumberOfValues(int minimumNumberOfValues) {
        this.minimumNumberOfValues = minimumNumberOfValues;
    }

    public boolean isLabel() {
        return name.equals(structure.getLabel());
    }

    public Field(String name) {
        this.name = name;
    }
}
