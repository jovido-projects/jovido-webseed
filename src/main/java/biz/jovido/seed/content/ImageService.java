package biz.jovido.seed.content;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Stephan Grundner
 */
@Service
public class ImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private AssetService assetService;

    private boolean applyStyle(OriginalImage original, String styleName, IMOperation operation) {
        if ("small".equals(styleName)) {
            operation.resize(300,null);
            return true;
        }

        return false;
    }

    public ConvertedImage getConvertedImage(OriginalImage original, String styleName) {
        ConvertedImage converted = original.getConvertedImages().stream()
                .filter(it -> styleName.equals(it.getStyleName()))
                .findFirst().orElse(null);

        if (converted == null) {
            Resource resource = assetService.getResource(original);
            try {
                IMOperation operation = new IMOperation();
                boolean ok = applyStyle(original, styleName, operation);
                if (ok) {
                    File sourceFile = resource.getFile();
                    operation.addImage(sourceFile.getAbsolutePath());

                    converted = new ConvertedImage();
                    converted.setStyleName(styleName);
                    File targetFile = assetService.getAssetFile(converted);
                    operation.addImage(targetFile.getAbsolutePath());

                    new ConvertCmd().run(operation);

                    converted = (ConvertedImage) assetService.saveAsset(converted);
                    original.addConvertedImage(converted);
                    assetService.saveAsset(original);
                }
            } catch (Exception e) {
                LOG.error("Unable to convert image", e);
            }
        }

        return converted;
    }
}
