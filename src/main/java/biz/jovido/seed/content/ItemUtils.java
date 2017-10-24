package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class ItemUtils {

    public static Item getCurrent(Item item) {
        if (item != null) {
            ItemHistory history = item.getHistory();
            if (history != null) {
                return history.getCurrent();
            }
        }

        return null;
    }

    public static boolean areTheSame(Item one, Item other) {
        if (one == null || other == null) {
            return false;
        }

        if (one == other) {
            return true;
        }

        return one.getId().equals(other.getId());
    }

    @SuppressWarnings("unchecked")
    public static String labelToString(Item item) {
        Sequence<String> sequence = (Sequence<String>) item.getLabel();
        StringBuilder builder = new StringBuilder(sequence.length() * 16);
        for (Payload<String> payload : sequence.getPayloads()) {
            builder.append(payload.getValue());
        }

        return builder.toString();
    }
}
