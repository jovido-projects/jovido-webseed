package biz.jovido.seed.util;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static <T, C extends Set<T>> Set<T> toSet(Supplier<C> setFactory, T... values) {
        return Stream.of(values).collect(Collectors.toCollection(setFactory));
    }

    public static <T> Set<T> toHashSet(T... values) {
        return toSet(HashSet::new, values);
    }

    private CollectionUtils() {}
}
