package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

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

    public Resource getResource(UUID uuid) {

        return new FileSystemResource("assets/" + uuid + ".asset");
    }

    public Asset saveAsset(Asset asset) {
        return assetRepository.save(asset);
    }
}
