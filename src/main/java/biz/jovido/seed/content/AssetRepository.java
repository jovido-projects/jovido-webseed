package biz.jovido.seed.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Asset findByUuid(UUID uuid);
}
