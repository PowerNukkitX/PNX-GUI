package cn.powernukkitx.gui.scheme;

import cn.powernukkitx.gui.Main;
import cn.powernukkitx.gui.util.MineTypeUtils;
import cn.powernukkitx.gui.util.ResourceUtils;
import org.cef.callback.CefCallback;
import org.cef.handler.CefResourceHandlerAdapter;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefRequest;
import org.cef.network.CefResponse;

import java.io.IOException;
import java.io.InputStream;

public class PNXResourceHandler extends CefResourceHandlerAdapter {
    protected final String url;
    protected final CefRequest cefRequest;

    protected String contentType = null;
    protected InputStream is = null;

    public PNXResourceHandler(CefRequest cefRequest) {
        this.url = cefRequest.getURL();
        this.cefRequest = cefRequest;
    }

    @Override
    public boolean processRequest(CefRequest request, CefCallback callback) {
        var url = this.url.substring("pnx://bundle".length());

        int pos = url.indexOf('/');
        if (pos < 0)
            return false;
        else
            url = url.substring(pos + 1);

        if (url.length() <= 0 || url.charAt(0) == '.') {
            System.err.println("Invalid URL " + url);
            return false;
        }

        is = ResourceUtils.getResource(url);
        if (is == null) {
            System.err.println("Resource " + url + " NOT found!");
            return false; //Mhhhhh... 404?
        }

        contentType = null;
        pos = url.lastIndexOf('.');
        if (pos >= 0 && pos < url.length() - 2)
            contentType = MineTypeUtils.mimeTypeFromExtension(url.substring(pos + 1));

        callback.Continue();

        return true;
    }

    @Override
    public void getResponseHeaders(CefResponse response, IntRef responseLength, StringRef redirectUrl) {
        if (contentType != null)
            response.setMimeType(contentType);
        response.setStatus(200);
        response.setStatusText("OK");
        if (is != null) {
            try {
                responseLength.set(is.available());
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        responseLength.set(-1);
    }

    // TODO: 2022/6/23 搞清楚callback改如何调用
    @Override
    public boolean readResponse(byte[] dataOut, int bytesToRead, IntRef bytesRead, CefCallback callback) {
        try {
            var ret = is.read(dataOut, 0, bytesToRead);
            if (ret <= 0)
                is.close();
            bytesRead.set(Math.max(ret, 0));
            callback.Continue();
            return ret > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
