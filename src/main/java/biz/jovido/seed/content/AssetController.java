package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    protected ResponseEntity<Resource> assetByUuid(@RequestParam(name = "uuid") UUID uuid) {
        Resource file = assetService.getResource(uuid);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping(path = "/files/{uuid}/{fileName:.+}")
    protected ResponseEntity<Resource> assetByUuidWithFileName(
            @PathVariable(name = "uuid") UUID uuid,
            @PathVariable(name = "fileName") String fileName) {

        Asset asset = assetService.getAsset(uuid);
        Resource file = assetService.getResource(asset);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + asset.getFileName() + "\"").body(file);
    }

    @GetMapping(path = "/images/{uuid}/{fileName:[a-zA-Z0-9]+\\.jpg}")
    protected ResponseEntity<Resource> imageByUuidWithStyleAndFileName(
            @PathVariable(name = "uuid") UUID uuid,
            @PathVariable(name = "fileName") String fileName,
            @RequestParam(name = "style", required = false) String styleName) {

        Asset asset = assetService.getAsset(uuid);
//        Resource file = assetService.getResource(asset);

        ConvertedImage convertedImage = imageService.getConvertedImage((OriginalImage) asset, styleName);
        Resource file = assetService.getResource(convertedImage);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + convertedImage.getFileName() + "\"").body(file);
    }
}
