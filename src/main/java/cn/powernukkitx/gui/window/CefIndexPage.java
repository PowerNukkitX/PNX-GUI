package cn.powernukkitx.gui.window;

import org.cef.CefClient;
import org.cef.browser.CefBrowser;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public record CefIndexPage(int id, CefBrowser browser, Component browserUI) {
    static final AtomicInteger globalIndexPageId = new AtomicInteger(0);

    public static CefIndexPage create(String url, CefClient cefClient) {
        var browser = cefClient.createBrowser(url, false, true);
        return new CefIndexPage(globalIndexPageId.getAndIncrement(), browser, browser.getUIComponent());
    }

    public void close() {
        browserUI.setVisible(false);
        browser.close(true);
    }
}