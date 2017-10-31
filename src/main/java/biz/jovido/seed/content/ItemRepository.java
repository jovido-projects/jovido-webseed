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

//    Page<Item> findAllByLeafIsNotNull(Pageable pageable);
    Page<Item> findAllByLeafIsNotNull(Pageable pageable);

    @Query("select i from Item i " +
            "join i.leaf l " +
            "where i = l.current " +
            "order by i.lastModifiedAt desc")
    List<Item> findAllCurrent();

    @Query("from Item i " +
            "join i.leaf l " +
            "where l.id = ?1 " +
            "and i = l.published")
    Item findPublished(Long leafId);

    @Query("select i from Item i " +
            "join i.leaf l " +
            "where i.path = ?1 " +
            "and i = l.published " +
            "order by i.lastModifiedAt desc")
    List<Item> findAllPublishedByPath(String path);
}
