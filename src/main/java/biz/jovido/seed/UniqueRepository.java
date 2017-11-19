package biz.jovido.seed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@NoRepositoryBean
public interface UniqueRepository<T extends Unique> extends JpaRepository<T, Long> {

    T findByUuid(UUID uuid);
}
