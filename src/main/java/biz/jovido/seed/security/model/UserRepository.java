package biz.jovido.seed.security.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
