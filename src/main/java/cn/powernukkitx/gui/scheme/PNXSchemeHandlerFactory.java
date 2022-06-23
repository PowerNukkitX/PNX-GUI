package cn.powernukkitx.gui.scheme;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefSchemeHandlerFactory;
import org.cef.handler.CefResourceHandler;
import org.cef.network.CefRequest;

public class PNXSchemeHandlerFactory implements CefSchemeHandlerFactory {
    @Override
    public CefResourceHandler create(CefBrowser cefBrowser, CefFrame cefFrame, String url, CefRequest cefRequest) {
        return new PNXResourceHandler(cefRequest);
    }
}
