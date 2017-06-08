package biz.jovido.seed.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface FragmentRepository extends JpaRepository<Fragment, Long> {

    @Query("from Payload p where p.id = ?1")
    Payload<?> findPayloadById(Long id);

//    Fragment findByLocaleAndPath()
}
