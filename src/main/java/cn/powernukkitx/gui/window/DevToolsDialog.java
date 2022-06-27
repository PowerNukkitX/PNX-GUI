package cn.powernukkitx.gui.window;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefLoadHandler;
import org.cef.network.CefRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.Serial;

public class DevToolsDialog extends JDialog {

    @Serial
    private static final long serialVersionUID = 6859581641415822180L;

    private final CefBrowser devTools_;

    public DevToolsDialog(Frame owner, String title, CefBrowser browser) {
        this(owner, title, browser, null);
    }

    public DevToolsDialog(Frame owner, String title, CefBrowser browser, Point inspectAt) {
        super(owner, title, false);

        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocation(owner.getLocation().x + 20, owner.getLocation().y + 20);

        devTools_ = browser.getDevTools(inspectAt);
        add(devTools_.getUIComponent());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                dispose();
            }
        });
    }

    @Override
    public void dispose() {
        devTools_.close(true);
        super.dispose();
    }
}
