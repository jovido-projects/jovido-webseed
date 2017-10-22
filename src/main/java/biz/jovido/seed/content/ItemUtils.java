package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class ItemUtils {

    public static Item getCurrent(Item item) {
        if (item != null) {
            History history = item.getHistory();
            if (history != null) {
                return history.getCurrent();
            }
        }

        return null;
    }
}
