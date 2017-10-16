package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    protected ResponseEntity<Resource> assetByUuid(@RequestParam(name = "uuid") UUID uuid) {
        Resource file = assetService.getResource(uuid);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
