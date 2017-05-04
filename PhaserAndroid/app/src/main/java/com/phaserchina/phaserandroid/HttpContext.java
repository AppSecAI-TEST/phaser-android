package com.phaserchina.phaserandroid;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by lixin on 17/5/3.
 */
public class HttpContext {

    private Socket underlySocket;

    private HashMap<String, String> requestHeaders;

    public HttpContext() {
        requestHeaders = new HashMap<String, String>();
    }

    public void setUnderlySocket(Socket socket) {
        this.underlySocket = socket;
    }

    public Socket getUnderlySocket() {
        return this.underlySocket;
    }

    public void addRequestHeaders(String headerName, String headerValue) {
        requestHeaders.put(headerName, headerValue);
    }

    public String getRequestHeaderValue(String headerName) {
        return requestHeaders.get(headerName);
    }

}
