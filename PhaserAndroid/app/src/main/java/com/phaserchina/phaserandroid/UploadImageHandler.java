package com.phaserchina.phaserandroid;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by lixin on 17/5/4.
 */
public class UploadImageHandler implements IResourceUriHandler {

    private String acceptPrefix = "/upload_image/";

    @Override
    public boolean accept(String uri) {
        return uri.startsWith(acceptPrefix);
    }

    @Override
    public void handle(String uri, HttpContext httpContext) throws IOException {
        OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
        PrintWriter writer = new PrintWriter(nos);
        writer.println("HTTP/1.1 200 OK");
        writer.println();
        writer.println("from upload image handler");
        writer.flush();
    }

}
