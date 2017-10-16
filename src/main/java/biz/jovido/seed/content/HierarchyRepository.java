package biz.jovido.seed.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface HierarchyRepository extends JpaRepository<Hierarchy, Long> {

    Hierarchy findByName(String name);
}
