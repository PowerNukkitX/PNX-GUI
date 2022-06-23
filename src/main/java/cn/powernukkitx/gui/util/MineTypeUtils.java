package cn.powernukkitx.gui.util;

import java.util.HashMap;
import java.util.Map;

public final class MineTypeUtils {

    public static final Map<String, String> mimeTypeMap = new HashMap<>();

    private MineTypeUtils() {

    }

    public static String mimeTypeFromExtension(String ext) {
        ext = ext.toLowerCase();
        String ret = mimeTypeMap.get(ext);
        if(ret != null)
            return ret;

        return switch (ext) {
            case "htm", "html" -> "text/html";
            case "css" -> "text/css";
            case "js" -> "text/javascript";
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            case "svg" -> "image/svg+xml";
            case "xml" -> "text/xml";
            case "txt" -> "text/plain";
            default -> null;
        };
    }
}
