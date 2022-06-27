package cn.powernukkitx.gui;

import cn.powernukkitx.gui.util.CollectionUtils;
import cn.powernukkitx.gui.window.DevToolsDialog;
import cn.powernukkitx.gui.window.MainWindow;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static MainWindow mainWindow;
    public static DevToolsDialog devToolsDialog;

    public static boolean debug = false;

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        doChores();
        debug = args.length > 0 && CollectionUtils.contains(args, "debug");
        try {
            mainWindow = new MainWindow(false, new String[0]);
            mainWindow.setVisible(true);
            if (debug) {
                FlatInspector.install( "ctrl shift alt X" );
                var timer = new Timer(1000, e -> {
                    devToolsDialog = new DevToolsDialog(mainWindow, "PowerNukkitX GUI DevTools", mainWindow.getPageManager().getSelectedPage().browser());
                    devToolsDialog.setVisible(true);
                });
                timer.setRepeats(false);
                timer.start();
            }
        } catch (UnsupportedPlatformException | CefInitializationException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void doChores() {
        UIManager.put("TabbedPane.tabClosable", true);
    }
}
