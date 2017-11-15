package biz.jovido.seed.util;

import java.util.function.Supplier;

/**
 * @author Stephan Grundner
 */
@Deprecated
public final class NullUtils {

    public static <T> T getOrNull(Supplier<T> resolver) {
        try {
            return resolver.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    private NullUtils() {}
}
