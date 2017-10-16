package biz.jovido.seed.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Branch findByHierarchyAndLocale(Hierarchy hierarchy, Locale locale);
}
