package com.example.explorenow.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PhotoRepository {

    private final Context context;

    public PhotoRepository(Context context) {
        this.context = context;
    }

    public String saveFromUri(Uri source) throws IOException {
        Bitmap bitmap;
        ContentResolver resolver = context.getContentResolver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.Source src = ImageDecoder.createSource(resolver, source);
            bitmap = ImageDecoder.decodeBitmap(src);
        } else {
            // Deprecated в новите версии, но работи за стари устройства
            bitmap = MediaStore.Images.Media.getBitmap(resolver, source);
        }

        return saveBitmap(bitmap);
    }

    /**
     * Запазва bitmap локално в filesDir
     */
    public String saveBitmap(Bitmap bitmap) throws IOException {
        String fileName = "landmark_" + UUID.randomUUID() + ".jpg";
        File file = new File(context.getFilesDir(), fileName);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return file.getAbsolutePath();
    }

    /**
     * Изтрива локален файл
     */
    public void deletePhoto(String path) {
        if (path == null) return;

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
