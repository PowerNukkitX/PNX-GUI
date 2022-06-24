package cn.powernukkitx.gui.util;

import cn.powernukkitx.gui.Main;
import cn.powernukkitx.gui.share.GUIConstant;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class ResourceUtils {
    public static final File devSourceDir;

    static {
        var resourceDir = new File(GUIConstant.userDir, "src/main/ui");
        if (resourceDir.exists() && resourceDir.isDirectory()) {
            devSourceDir = resourceDir;
        } else {
            resourceDir = new File(GUIConstant.userDir.getParentFile(), "src/main/ui");
            if (resourceDir.exists() && resourceDir.isDirectory()) {
                devSourceDir = resourceDir;
            } else {
                devSourceDir = null;
            }
        }
    }

    private ResourceUtils() {

    }

    public static @Nullable InputStream getResource(String name) {
        if (devSourceDir != null) {
            System.out.println("Reading " + name + " from dev source.");
            var file = new File(devSourceDir, name);
            if (file.exists()) {
                try {
                    return new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    return null;
                }
            }
        } else {
            System.out.println("Reading " + name + " from bundle.");
            return Main.class.getClassLoader().getResourceAsStream(name);
        }
        return null;
    }
}
