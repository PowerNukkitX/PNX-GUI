package cn.powernukkitx.gui.util;

import cn.powernukkitx.gui.Main;
import cn.powernukkitx.gui.share.GUIConstant;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

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

    public static @Nullable Icon getIcon(String iconPath, int width, int height) {
        try {
            if (iconPath.endsWith("svg")) {
                return new FlatSVGIcon(getResource(iconPath)).derive(width, height);
            } else {
                return new ImageIcon(ImageIO.read(Objects.requireNonNull(ResourceUtils.getResource(iconPath))).getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
