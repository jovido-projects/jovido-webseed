package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class PayloadUtils {

    @SuppressWarnings("unchecked")
    public static  <T extends Payload> T getPayload(Fragment fragment, String attributeName, int index) {
        Attribute attribute = fragment.getAttribute(attributeName);
        return  (T) attribute.getPayloads().get(index);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Fragment fragment, String attributeName, int index) {
        Attribute attribute = fragment.getAttribute(attributeName);
        Payload<?> payload = attribute.getPayloads().get(index);
        return (T) payload.getValue();
    }
}
