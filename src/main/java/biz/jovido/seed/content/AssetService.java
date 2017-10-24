package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public Asset getAsset(Long id) {
        Asset asset = assetRepository.findOne(id);

        return asset;
    }

    public Asset getAsset(UUID uuid) {
        return assetRepository.findByUuid(uuid);
    }

    public Resource getResource(UUID uuid) {

        return new FileSystemResource("assets/" + uuid + ".asset");
    }

    public Resource getResource(Asset asset) {
        return getResource(asset.getUuid());
    }

    public Asset saveAsset(Asset asset) {
        return assetRepository.saveAndFlush(asset);
    }

    public String getRelativeUrl(Asset asset) {
        return String.format("/asset/files/%s/%s", asset.getUuid(), asset.getFileName());
    }
}
