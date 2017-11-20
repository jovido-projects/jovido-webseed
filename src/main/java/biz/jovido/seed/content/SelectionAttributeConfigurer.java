package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class SelectionAttributeConfigurer extends AttributeConfigurer<SelectionAttribute, SelectionAttributeConfigurer> {

    public SelectionAttributeConfigurer setMultiselect(boolean multiselect) {
        attribute.setMultiselect(multiselect);

        return this;
    }

    public SelectionAttributeConfigurer addOption(String option) {
        attribute.getOptions().add(option);

        return this;
    }

    public SelectionAttributeConfigurer(StructureConfigurer builder, SelectionAttribute attribute) {
        super(builder, attribute);
    }

}
