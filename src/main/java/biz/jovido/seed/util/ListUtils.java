package biz.jovido.seed.util;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public final class ListUtils {

    public static <E, L extends List<E>> void add(L list, E element) {
        if (!list.add(element)) {
            throw new RuntimeException("Unable to add element to list");
        }
    }

    private ListUtils() {}
}
