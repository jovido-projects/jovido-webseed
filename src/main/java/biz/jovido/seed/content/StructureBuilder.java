package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class StructureBuilder implements StructureConfigurer {

    private Structure structure;

    @Override
    public TextFieldConfigurer addTextField(String name) {
        TextFieldConfigurer fieldConfigurer = new TextFieldConfigurer();
        TextField field = new TextField(name);
        structure.putField(field);
        fieldConfigurer.setField(field);
        fieldConfigurer.setBuilder(this);
        return fieldConfigurer;
    }

    public StructureBuilder(Structure structure) {
        this.structure = structure;
    }
}
