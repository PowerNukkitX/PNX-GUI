package cn.powernukkitx.gui.window;

import org.cef.CefClient;
import org.cef.browser.CefBrowser;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class CefIndexPage {
    static final AtomicInteger globalIndexPageId = new AtomicInteger(0);
    private final int id;
    private String title;
    private final CefBrowser browser;
    private final Component browserUI;

    public CefIndexPage(
            int id,
            CefBrowser browser,
            Component browserUI
    ) {
        this.id = id;
        this.browser = browser;
        this.browserUI = browserUI;
    }

    public static CefIndexPage create(String url, CefClient cefClient) {
        var browser = cefClient.createBrowser(url, false, true);
        return new CefIndexPage(globalIndexPageId.getAndIncrement(), browser, browser.getUIComponent());
    }

    public int id() {
        return id;
    }

    public CefBrowser browser() {
        return browser;
    }

    public Component browserUI() {
        return browserUI;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CefIndexPage that = (CefIndexPage) o;

        if (id != that.id) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(browser, that.browser)) return false;
        return Objects.equals(browserUI, that.browserUI);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (browser != null ? browser.hashCode() : 0);
        result = 31 * result + (browserUI != null ? browserUI.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CefIndexPage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", browser=" + browser +
                ", browserUI=" + browserUI +
                '}';
    }
}
