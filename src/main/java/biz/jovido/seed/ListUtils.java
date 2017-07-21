package biz.jovido.seed;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public class ListUtils {

    public static <T> T getOrNull(List<T> list, int index) {
        if (index >= list.size()) {
            return null;
        }

        return list.get(index);
    }
}
