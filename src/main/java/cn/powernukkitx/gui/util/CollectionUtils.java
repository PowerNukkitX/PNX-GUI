package cn.powernukkitx.gui.util;

import java.util.Objects;
import java.util.function.Predicate;

public final class CollectionUtils {
    private CollectionUtils() {

    }

    public static <T> T find(T[] array, Predicate<T> predicate) {
        for (T t : array) {
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }

    public static <T> T find(T[] array, T value) {
        return find(array, (t) -> Objects.equals(t, value));
    }

    public static boolean contains(Object[] array, Object value) {
        return find(array, value) != null;
    }
}
