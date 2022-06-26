package cn.powernukkitx.gui.window;

import cn.powernukkitx.gui.util.ResourceUtils;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class CefIndexPage {
    static final Icon closeIcon = ResourceUtils.getIcon("image/close.svg", 10, 10);

    static final AtomicInteger globalIndexPageId = new AtomicInteger(0);
    private final int id;

    private Icon icon;
    private String title;

    private final CefBrowser browser;
    private final Component browserUI;

    private CloseListener closeListener = null;

    private CefIndexPage(
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

    public CefIndexPage setTitle(String title) {
        this.title = title;
        return this;
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

    public Icon getIcon() {
        return icon;
    }

    public CefIndexPage setIcon(Icon icon) {
        this.icon = icon;
        return this;
    }

    public JComponent createTabComponent() {
        var label = new JLabel(title, icon, JLabel.LEADING);
        var close = new JButton(closeIcon);
        close.putClientProperty("JButton.buttonType", "borderless");
        close.putClientProperty("JComponent.outline", new Color(60, 63, 65, 1));
        close.addActionListener(e -> {
            if (closeListener != null && closeListener.onClose(this)) {
                browserUI.setVisible(false);
                browser.close(true);
            }
        });
        var box = Box.createHorizontalBox();
        box.add(label);
        box.add(Box.createHorizontalStrut(6));
        box.add(close);
        return box;
    }

    public CloseListener getCloseListener() {
        return closeListener;
    }

    public CefIndexPage setCloseListener(CloseListener closeListener) {
        this.closeListener = closeListener;
        return this;
    }

    public interface CloseListener {
        /**
         *
         * @param self 标签页自身
         * @return 如果为true则允许关闭
         */
        boolean onClose(CefIndexPage self);
    }
}
