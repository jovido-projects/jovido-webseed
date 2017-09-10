package biz.jovido.seed.content.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Repository
public interface RootRepository extends JpaRepository<Root, Long> {

    Root findByHierarchyAndLocale(Hierarchy hierarchy, Locale locale);
}
