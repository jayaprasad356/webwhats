package com.vedha.whatsweb;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Common {

    static final int MINI_KIND = 1;
    static final int MICRO_KIND = 3;

    public static final int GRID_COUNT = 2;

    private static final String CHANNEL_NAME = "GRAYMATTER";

    public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory() +
            File.separator + "WhatsApp/Media/.Statuses");

    public static final File STATUS_DIRECTORY_NEW = new File(Environment.getExternalStorageDirectory() +
            File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");

    public static String APP_DIR=Environment.getExternalStorageDirectory().getPath() +
    File.separator + "Whats Web";;

    public static void copyFiles(String status, Context context) {

        File file = new File(Common.APP_DIR);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Toast.makeText(context.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

        String fileName;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());

        if (isVideo(status)) {
            fileName = "VID_" + currentDateTime + ".mp4";
        } else {
            fileName = "IMG_" + currentDateTime + ".jpg";
        }

        File destFile = new File(file + File.separator + fileName);

        try {
            copyFile(status, destFile);
            destFile.setLastModified(System.currentTimeMillis());
            new SingleMediaScanner(context, file);
            showNotification(context, destFile, status);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean isVideo(String status) {
        if (status.endsWith(".mp4"))
            return true;
        return false;
    }



    private static void copyFile(String src, File dst) throws IOException {

        File from = new File(src);
        File to = new File(dst.getPath());
        from.renameTo(to);
    }

    private static void showNotification(Context context , File destFile, String status) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel(context);
        }

        Uri data = FileProvider.getUriForFile(context, "a.gautham.statusdownloader" + ".provider", new File(destFile.getAbsolutePath()));
        Intent intent = new Intent(Intent.ACTION_VIEW);

        if (isVideo(status)) {
            intent.setDataAndType(data, "video/*");
        } else {
            intent.setDataAndType(data, "image/*");
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context, CHANNEL_NAME);


        Toast.makeText(context, "Saved to " + Common.APP_DIR, Toast.LENGTH_LONG).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void makeNotificationChannel(Context context) {

        NotificationChannel channel = new NotificationChannel(Common.CHANNEL_NAME, "Saved", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setShowBadge(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

}
