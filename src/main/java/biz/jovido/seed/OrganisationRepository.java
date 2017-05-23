package biz.jovido.seed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
}
