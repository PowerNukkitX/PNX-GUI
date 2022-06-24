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
            mainWindow = new MainWindow(false, new String[0]);
            var welcomeWindow = mainWindow.addIndexPage("pnx://bundle/html/index.html", "image/welcome.svg", "欢迎", "PNX-GUI欢迎页");
            var aboutWindow = mainWindow.addIndexPage("pnx://bundle/html/about.html", "image/about.svg", "关于", "关于PNX-GUI");
            mainWindow.setVisible(true);
        } catch (UnsupportedPlatformException | CefInitializationException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
