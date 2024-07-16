package com.example.myasynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFromNetwork {

    public static Bitmap downloadImage(String url){
        Bitmap bm = null;
        try {
            InputStream inputStream = new URL(url).openStream();
            bm = BitmapFactory.decodeStream(inputStream);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bm;
    }
}
//Marcel Parzyszek 4P 14.03.2024 22:30