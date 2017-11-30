package biz.jovido.seed.util;

import java.util.Map;

/**
 * @author Stephan Grundner
 */
public final class MapUtils {

    public static <K, V, M extends Map<K, V>> void putOnce(M map, K key, V value) {
        if (map.containsKey(key)) {
            throw new RuntimeException(String.format("Key [%s] is present", key));
        }

        V replaced = map.put(key, value);
        if (replaced != null) {
            throw new RuntimeException(String.format("Value replaced for key [%s]", key));
        }
    }

    private MapUtils() {}
}
