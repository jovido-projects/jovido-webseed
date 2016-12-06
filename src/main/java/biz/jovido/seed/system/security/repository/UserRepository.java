package biz.jovido.seed.system.security.repository;

import biz.jovido.seed.system.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
