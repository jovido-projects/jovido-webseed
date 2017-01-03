package biz.jovido.seed.util;

import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Deprecated
public final class CollectionUtils {

//    public static <T> void resize(List<T> list, int size) {
//        int difference = size - list.size();
//        if (difference > 0) {
//            for (int i = 0; i < difference; i++) {
//                list.add(null);
//            }
//
//            Assert.isTrue(list.size() == size);
//        } else if (difference < 0) {
//            for (int i = difference; i < 0; i++) {
//                list.remove(list.size() - 1);
//            }
//        }
//    }
//
//    public static <T> void increase(List<T> list, int n) {
//        resize(list, list.size() + n);
//    }
//
//    public static <T> void decrease(List<T> list, int n) {
//        resize(list, list.size() - n);
//    }

    private CollectionUtils() {}
}
