package biz.jovido.seed.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);
}
