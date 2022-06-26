package cn.powernukkitx.gui.util;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggerUtils {
    private LoggerUtils() {

    }

    public static void forceDisableLogger(String name, String resourceBundle, Class<?> callerClass) {
        try {
            var method = Logger.class.getDeclaredMethod("getLogger", String.class, String.class, Class.class);
            method.setAccessible(true);
            var result = (Logger) method.invoke(null, name, resourceBundle, callerClass);
            result.setLevel(Level.OFF);
            result.setFilter(record -> {
                System.out.println(record.getMessage());
                return false;
            });
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
