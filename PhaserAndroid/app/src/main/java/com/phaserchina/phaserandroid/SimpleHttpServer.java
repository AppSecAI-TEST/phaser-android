package com.phaserchina.phaserandroid;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lixin on 17/5/3.
 */
public class SimpleHttpServer {

    private final WebConfiguration webConfig;
    private final ExecutorService threadPool;
    private boolean isEnable = false;
    private ServerSocket socket;
    private Set<IResourceUriHandler> resourceHandlers;

    public SimpleHttpServer(WebConfiguration webConfig) {
        this.webConfig = webConfig;
        threadPool = Executors.newCachedThreadPool();
        resourceHandlers = new HashSet<IResourceUriHandler>();
    }

    /**
     * 启动server（异步）
     */
    public void startAsync() {
        isEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProcSync();
            }
        }).start();
    }

    /**
     * 停止server（异步）
     */
    public void stopAsync() throws IOException {
        if(!isEnable) {
            return;
        }
        isEnable = false;
        socket.close();
        socket = null;
    }

    private void doProcSync() {
        try {
            InetSocketAddress socketAddr = new InetSocketAddress(webConfig.getPort());
            socket = new ServerSocket();
            socket.bind(socketAddr);
            while(isEnable) {
                final Socket remotePeer = socket.accept();
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("spy", "a remote peer accepted..." + remotePeer.getRemoteSocketAddress().toString());
                        onAcceptRemotePeer(remotePeer);
                    }
                });
            }
        } catch (IOException e) {
            Log.e("spy", e.toString());
        }
    }

    public void registerResourceHandler(IResourceUriHandler handler) {
        resourceHandlers.add(handler);
    }

    private void onAcceptRemotePeer(Socket remotePeer) {
        try {
            HttpContext httpContext = new HttpContext();
            httpContext.setUnderlySocket(remotePeer);
            InputStream nis = remotePeer.getInputStream();
            String headerLine = null;
            String resourceUri = headerLine = StreamToolkit.readLine(nis).split(" ")[1];
            Log.d("spy", resourceUri);
            while((headerLine = StreamToolkit.readLine(nis)) != null) {
                String[] pair = headerLine.split(": ");
                if(pair.length > 1) {
                    httpContext.addRequestHeaders(pair[0], pair[1]);
                }
                Log.d("spy", "header line = " + headerLine);
            }
            for (IResourceUriHandler handler : resourceHandlers) {
                if(!handler.accept(resourceUri)) {
                    continue;
                }
                handler.handle(resourceUri, httpContext);
            }
        } catch (IOException e) {
            Log.e("spy", e.toString());
        } finally {
            try {
                remotePeer.close();
            } catch (IOException e) {
                Log.e("spy", e.toString());
            }
        }
    }

}
