package com.example.myasynctask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar progressBar;
    private Bitmap image;

    //Image path
    private static final String imagepath = "https://static.cargurus.com/images/site/2008/05/10/06/32/2003_citroen_c3-pic-34415.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progress_bar);

        createNotificationChannel();

        if (Build.VERSION_CODES.Q >= Build.VERSION.SDK_INT) {
//            MyAsyncTask myAsyncTask = new MyAsyncTask(this, imageView);
//            myAsyncTask.execute(imagepath);
        } else {
//            executeMyService();
            executeMyService2();
        }
    }
//Nie potrzebne tylko do testu
//    private void executeMyService() {
//        ExecutorService service = Executors.newSingleThreadExecutor();
//        service.execute(() -> {
//            runOnUiThread(() -> {
//                progressBar.setVisibility(View.VISIBLE);
//            });
//
//            Bitmap image = DownloadFromNetwork.downloadImage(imagepath);
//
//            runOnUiThread(() -> {
//                progressBar.setVisibility(View.GONE);
//                if (image != null) {
//                    imageView.setImageBitmap(image);
//                }
//            });
//        });
//    }
    private void executeMyService2() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setContentTitle("Pobieranie obrazu")
                .setSmallIcon(R.drawable.baseline_image_24);

        final int notificationId = 1;
        final int maxProgress = 100;
        builder.setProgress(maxProgress, 0, false);
        notificationManager.notify(notificationId, builder.build());
        ExecutorService service2 = Executors.newSingleThreadExecutor();
        service2.execute(() -> {
            int currentProgress = 0;

            image = DownloadFromNetwork.downloadImage(imagepath);

            while (currentProgress < maxProgress) {
                currentProgress += 1;
                builder.setContentTitle("Pobieranie Obrazu w toku "
                                +"-> "+ currentProgress +"%")
                        .setProgress(maxProgress, currentProgress, false)
                        .setOnlyAlertOnce(true);
                notificationManager.notify(notificationId, builder.build());
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runOnUiThread(() -> {
                if (image != null) {
                    imageView.setImageBitmap(image);
                    builder.setContentTitle("Pobieranie Powiodło sie!")
                            .setProgress(0, 0, false)
                            .setOnlyAlertOnce(false);

                    progressBar.setVisibility(View.GONE);
                    notificationManager.notify(notificationId, builder.build());
                }
            });
        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Nazwa Kanału";
            String description = "Opis Kanału";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("id_Kanału", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
//Marcel Parzyszek 4P 14.03.2024 22:30