package biz.jovido.seed.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

//    Page<Item> findAllByLeafIsNotNull(Pageable pageable);
    Page<Item> findAllByLeafIsNotNull(Pageable pageable);
}
