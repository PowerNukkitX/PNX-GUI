package cn.powernukkitx.gui;

import cn.powernukkitx.gui.window.MainWindow;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static MainWindow mainWindow;

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        FlatInspector.install( "ctrl shift alt X" );
        doChores();
        try {
            mainWindow = new MainWindow(false, new String[0]);
            mainWindow.setVisible(true);
        } catch (UnsupportedPlatformException | CefInitializationException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void doChores() {
        UIManager.put("TabbedPane.tabClosable", true);
    }
}
