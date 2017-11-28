package biz.jovido.seed.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i " +
            "join i.history h " +
            "where i = h.current " +
            "order by i.lastModifiedAt desc")
    List<Item> findAllCurrent();

    @Query("from Item i " +
            "join i.history h " +
            "where h.id = ?1 " +
            "and i = h.published")
    Item findPublished(Long historyId);

    @Query("select i from Item i " +
            "join i.history h " +
            "where i.path = ?1 " +
            "and i = h.published " +
            "order by i.lastModifiedAt desc")
    List<Item> findAllPublishedByPath(String path);
}
