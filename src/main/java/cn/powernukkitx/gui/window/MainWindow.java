package cn.powernukkitx.gui.window;

import cn.powernukkitx.gui.scheme.PNXSchemeHandlerFactory;
import cn.powernukkitx.gui.share.GUIConstant;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefSchemeRegistrar;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Locale;

public final class MainWindow extends JFrame {
    private final CefApp cefApp;
    private final CefClient client;
    private final PageManager pageManager;
    private boolean browserFocus = true;

    public MainWindow(boolean useOSR, String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        if (args == null) {
            args = new String[0];
        }

        pageManager = new PageManager();

        setTitle("PowerNukkitX");
        setIconImage(Toolkit.getDefaultToolkit().createImage(MainWindow.class.getClassLoader().getResource("image/pnx.png")));

        var builder = new CefAppBuilder();
        builder.getCefSettings().locale = Locale.getDefault().toLanguageTag().replace("_", "-");
        builder.getCefSettings().windowless_rendering_enabled = useOSR;
        builder.getCefSettings().cache_path = GUIConstant.cacheDir.getAbsolutePath();
        builder.getCefSettings().background_color = builder.getCefSettings().new ColorType(255, 60, 63, 65);
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                if (state == CefApp.CefAppState.TERMINATED) System.exit(0);
            }

            @Override
            public void onRegisterCustomSchemes(CefSchemeRegistrar registrar) {
                registrar.addCustomScheme("pnx", true, true, true, true, true, true, true);
            }

            @Override
            public void onContextInitialized() {
                cefApp.registerSchemeHandlerFactory("pnx", "bundle", new PNXSchemeHandlerFactory());
            }
        });

        if (args.length > 0) {
            builder.addJcefArgs(args);
        }

        cefApp = builder.build();
        client = cefApp.createClient();

        var msgRouter = CefMessageRouter.create();
        client.addMessageRouter(msgRouter);

        client.addFocusHandler(new CefFocusHandlerAdapter() {
            @Override
            public void onGotFocus(CefBrowser browser) {
                if (browserFocus) return;
                browserFocus = true;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                browser.setFocus(true);
            }

            @Override
            public void onTakeFocus(CefBrowser browser, boolean next) {
                browserFocus = false;
            }
        });

        client.addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onTitleChange(CefBrowser browser, String title) {
                setTitle(title);
            }
        });

        pageManager.addPage(CefIndexPage.create("pnx://bundle/html/index.html", client));
        add(pageManager.selectPage(0).browserUI());
        pack();
        setSize(800, 600);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                dispose();
            }
        });
    }

    public CefApp getCefApp() {
        return cefApp;
    }

    public CefClient getClient() {
        return client;
    }

    public boolean isBrowserFocus() {
        return browserFocus;
    }

    public PageManager getPageManager() {
        return pageManager;
    }
}
