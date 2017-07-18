package biz.jovido.seed.content;

import java.util.Collection;

/**
 * @author Stephan Grundner
 */
public class FragmentUtils {

    public static Fragment getEnclosingFragment(Fragment fragment) {
        if (fragment.isReferred()) {
            FragmentPayload payload = fragment.getReferringPayload();
            return getEnclosingFragment(payload.getField().getFragment());
        }

        return fragment;
    }

//    public static Structure getStructure(Fragment fragment) {
//        fragment = getEnclosingFragment(fragment);
//        return fragment.getStructure();
//    }

    public static void walkFragments(Fragment fragment, FragmentVisitor visitor) {
        if (fragment != null) {
            visitor.visitFragment(fragment);
            Collection<Field> fields = fragment.getFields().values();
            for (Field field : fields) {
                for (Payload payload : field.getPayloads()) {
                    if (payload instanceof FragmentPayload) {
                        walkFragments(((FragmentPayload) payload).getValue(), visitor);
                    }
                }
            }
        }
    }
}
