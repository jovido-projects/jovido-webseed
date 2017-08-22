package biz.jovido.seed.security.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String authority);
}
