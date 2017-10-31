package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class ItemUtils {

    public static Item getCurrent(Item item) {
        if (item != null) {
            Leaf history = item.getLeaf();
            if (history != null) {
                return history.getCurrent();
            }
        }

        return null;
    }

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
}
