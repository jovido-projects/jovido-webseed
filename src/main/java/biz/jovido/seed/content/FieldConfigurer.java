package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class FieldConfigurer<F extends Field, C extends FieldConfigurer<F, C>> implements StructureConfigurer {

    private StructureBuilder builder;
    protected F field;

    /* public */ void setBuilder(StructureBuilder builder) {
        this.builder = builder;
    }

    /* public */ void setField(F field) {
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    public C setOrdinal(int ordinal) {
        field.setOrdinal(ordinal);

        return (C) this;
    }

    @SuppressWarnings("unchecked")
    public C setCapacity(int capacity) {
        field.setCapacity(capacity);

        return (C) this;
    }

    @SuppressWarnings("unchecked")
    public C setRequired(int required) {
        field.setRequired(required);

        return (C) this;
    }

    @Override
    public TextFieldConfigurer addTextField(String name) {
        return builder.addTextField(name);
    }
}
