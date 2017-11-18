package biz.jovido.seed.content;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class ItemUtils {

    @Deprecated
    public static boolean areTheSame(Item one, Item other) {
        if (one == null || other == null) {
            return false;
        }

        if (one == other) {
            return true;
        }

        return one.getId().equals(other.getId());
    }

    public static List<Payload> getPayloads(Item item, String attributeName) {
        return item.getPayloads().stream()
                .filter(it -> it.getAttributeName().equals(attributeName))
                .sorted(Comparator.comparingInt(Payload::getOrdinal))
                .collect(Collectors.toList());
    }
}
