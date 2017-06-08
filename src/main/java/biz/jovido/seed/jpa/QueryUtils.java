package biz.jovido.seed.jpa;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class QueryUtils {

    public static <T> T getSingleResult(TypedQuery<T> query) {
        List<T> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        }

        return null;
    }
}
