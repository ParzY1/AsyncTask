package com.example.myasynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAsyncTask extends AsyncTask<String, String, Bitmap> {

    public ProgressDialog dialog;
    public Bitmap image;
    public ImageView imageView;
    Context context;


    public MyAsyncTask(ImageView imageView, Context context){
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        image = null;
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            image = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute(){
        Log.v("task", "-----------------------------------------> Start onPreExecute()");
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }
    @Override
    protected void onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);
        dialog.cancel();
        imageView.setImageBitmap(bitmap);
    }

}
//Marcel Parzyszek 4P 14.03.2024 22:30
