package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class StructureBuilder extends StructureConfigurer {

    private final Structure structure;

    @Override
    public TextAttributeConfigurer addTextAttribute(String fieldName) {
        TextAttribute attribute = (TextAttribute) structure.getAttribute(fieldName);
        if (attribute == null) {
            attribute = new TextAttribute(structure, fieldName);
        }
        return new TextAttributeConfigurer(this, attribute);
    }

    @Override
    public FragmentAttributeConfigurer addFragmentAttribute(String fieldName) {
        FragmentAttribute attribute = (FragmentAttribute) structure.getAttribute(fieldName);
        if (attribute == null) {
            attribute = new FragmentAttribute(structure, fieldName);
        }
        return new FragmentAttributeConfigurer(this, attribute);
    }

    @Override
    public AssetAttributeConfigurer addAssetAttribute(String fieldName) {
        AssetAttribute attribute = (AssetAttribute) structure.getAttribute(fieldName);
        if (attribute == null) {
            attribute = new AssetAttribute(structure, fieldName);
        }
        return new AssetAttributeConfigurer(this, attribute);
    }

    @Override
    public Structure getStructure() {
        return structure;
    }

    public StructureBuilder(Structure structure) {
        this.structure = structure;
    }
}
