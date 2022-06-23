package cn.powernukkitx.gui;

import cn.powernukkitx.gui.window.MainWindow;
import com.formdev.flatlaf.FlatDarculaLaf;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;

import java.io.IOException;

public class Main {
    public static MainWindow mainWindow;

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        try {
            mainWindow = new MainWindow("pnx://bundle/html/index.html", false, true, new String[0]);
            mainWindow.setVisible(true);
        } catch (UnsupportedPlatformException | CefInitializationException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
