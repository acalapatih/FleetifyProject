package com.acalapatih.fleetifyproject.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CameraXUtil {

    private static final String FILENAME_FORMAT = "dd-MMM-yyyy";
    private static final int MAXIMAL_SIZE = 1000000;

    private static String getTimeStamp() {
        return new SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis());
    }

    public static File createCustomTempFile(Context context) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timeStamp = getTimeStamp();
        try {
            return File.createTempFile(timeStamp, ".jpg", storageDir);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void rotateFile(File file, boolean isBackCamera) {
        try {
            Matrix matrix = new Matrix();
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            float rotation = isBackCamera ? 0f : -90f;
            matrix.postRotate(rotation);
            if (!isBackCamera) {
                matrix.postScale(-1f, 1f, bitmap.getWidth() / 2f, bitmap.getHeight() / 2f);
            }
            Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            result.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File reduceFileImage(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());

        int compressQuality = 100;
        int streamLength;

        try {
            do {
                ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
                byte[] bmpPicByteArray = bmpStream.toByteArray();
                streamLength = bmpPicByteArray.length;
                compressQuality -= 5;
            } while (streamLength > MAXIMAL_SIZE);

            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
}

