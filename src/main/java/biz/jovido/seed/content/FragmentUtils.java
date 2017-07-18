package biz.jovido.seed.content;

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
}
