package com.vedha.whatsstatussaver.extras;

import android.os.Environment;

import java.io.File;

public class DirConstants {

    public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory() +
            File.separator + "WhatsApp/Media/.Statuses");

    public static final File STATUS_DIRECTORY_NEW = new File(Environment.getExternalStorageDirectory() +
            File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
}
