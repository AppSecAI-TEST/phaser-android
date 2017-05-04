package com.phaserchina.phaserandroid;

import java.io.IOException;

/**
 * Created by lixin on 17/5/4.
 */
public interface IResourceUriHandler {

    boolean accept(String uri);

    void handle(String uri, HttpContext httpContext) throws IOException;

}
