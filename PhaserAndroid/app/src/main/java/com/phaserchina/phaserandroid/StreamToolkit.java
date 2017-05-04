package com.phaserchina.phaserandroid;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by lixin on 17/5/3.
 */
public class StreamToolkit {

    public static String readLine(InputStream nis) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c1 = 0;
        int c2 = 0;
        while(c2 != -1 && !(c1 == '\r' && c2 == '\n')) {
            c1 = c2;
            c2 = nis.read();
            if(c2 != -1 && c2 != '\r' && c2 != '\n') {
                sb.append((char) c2);
            }
        }
        if(sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public static byte[] readRawFromStream(InputStream fis) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int nReaded;
        while( (nReaded = fis.read(buffer)) > 0 ) {
            bos.write(buffer, 0, nReaded);
        }
        return bos.toByteArray();
    }

}
