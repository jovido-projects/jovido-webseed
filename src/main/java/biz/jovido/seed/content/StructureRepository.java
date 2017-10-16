package biz.jovido.seed.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {

    Structure findByTypeAndRevision(Type type, int revision);

//    @Query("from Structure s " +
//            "join s.type as t " +
//            "where t.active = s " +
//            "and s.type = ?1")
//    Structure findByType(Type type);
//
//    @Query("from Structure s " +
//            "join s.type as t " +
//            "where t.active = s " +
//            "and t.name = ?1")
//    Structure findByTypeName(String typeName);
//
//    @Query("select s from Structure s " +
//            "join s.type as t " +
//            "where t.active = s " +
//            "and s.standalone = true")
//    List<Structure> findAllStandalone();

    List<Structure> findAllByPublishableIsTrue();
}
