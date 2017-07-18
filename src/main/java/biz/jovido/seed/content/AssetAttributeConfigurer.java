package biz.jovido.seed.content;

import java.util.Arrays;

/**
 * @author Stephan Grundner
 */
public class AssetAttributeConfigurer extends AttributeConfigurer<AssetAttribute, AssetAttributeConfigurer> {

    public AssetAttributeConfigurer addFileNameExtension(String fileNameExtension) {
        attribute.getFileNameExtensions().add(fileNameExtension);

        return this;
    }

    public AssetAttributeConfigurer addFileNameExtensions(String... fileNameExtensions) {
        attribute.getFileNameExtensions().addAll(Arrays.asList(fileNameExtensions));

        return this;
    }

    public AssetAttributeConfigurer setContentLengthLimit(long contentLengthLimit) {
        attribute.setContentLengthLimit(contentLengthLimit);

        return this;
    }

//    public AssetAttributeConfigurer setMaxLength(int maxLength) {
//        attribute.setMaxLength(maxLength);
//
//        return this;
//    }

    public AssetAttributeConfigurer(StructureBuilder builder, AssetAttribute attribute) {
        super(builder, attribute);
    }
}
