package biz.jovido.seed.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public final class CollectionUtils {

    public static <T> T getFirst(Collection<T> collection) {
        if (collection != null) {
            Iterator<T> iterator = collection.iterator();
            return iterator.hasNext() ? iterator.next() : null;
        }

        return null;
    }

    public static <T> T getFirstValue(Map<?, T> map) {
        if (map != null) {
            return getFirst(map.values());
        }

        return null;
    }

    private CollectionUtils() {}
}
