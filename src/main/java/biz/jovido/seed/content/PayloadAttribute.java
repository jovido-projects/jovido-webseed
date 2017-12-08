package biz.jovido.seed.content;

import biz.jovido.seed.ui.Field;

/**
 * @author Stephan Grundner
 */
public abstract class PayloadAttribute<T> {

    private FragmentStructure structure;
    private String name;
    private int ordinal = -1;

    private int required = 1;
    private int capacity = 1;

    public FragmentStructure getStructure() {
        return structure;
    }

    protected void setStructure(FragmentStructure structure) {
        this.structure = structure;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public int getOrdinal() {
        return ordinal;
    }

    protected void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public abstract Payload<T> createPayload();

    public Field<T> createField() {
        return null;
    }
}
