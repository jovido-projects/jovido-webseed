package biz.jovido.seed.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

//    Page<Item> findAllByHistoryIsNotNull(Pageable pageable);
    Page<Item> findAllByHistoryIsNotNull(Pageable pageable);

    @Query(value = "select i from Item i " +
            "join i.history h " +
            "where i = h.current")
    List<Item> findAllCurrent();
}
