package com.phaserchina.phaserandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SimpleHttpServer shs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebConfiguration wc = new WebConfiguration();
        wc.setPort(8088);
        wc.setMaxParallels(50);
        shs = new SimpleHttpServer(wc);
        shs.registerResourceHandler(new ResourceInAssetsHandler(MainActivity.this));
        shs.registerResourceHandler(new UploadImageHandler());
        shs.startAsync();
    }

    @Override
    protected void onDestroy() {
        try {
            shs.stopAsync();
        } catch (IOException e) {
            Log.e("spy", e.toString());
        }
        super.onDestroy();
    }
}
