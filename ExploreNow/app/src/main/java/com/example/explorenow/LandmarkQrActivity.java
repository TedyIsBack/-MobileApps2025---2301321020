package com.example.explorenow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.explorenow.utils.QrUtils;
import com.google.zxing.WriterException;

public class LandmarkQrActivity extends AppCompatActivity {

    private static final String EXTRA_QR_DATA = "qr_data";

    private ImageView imgQr;

    public static void start(Context context, String qrData) {
        Intent intent = new Intent(context, LandmarkQrActivity.class);
        intent.putExtra(EXTRA_QR_DATA, qrData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_qr);

        imgQr = findViewById(R.id.imgQr);

        String data = getIntent().getStringExtra(EXTRA_QR_DATA);
        if (data != null) {
            try {
                Bitmap qrBitmap = QrUtils.generateQrBitmap(data);
                imgQr.setImageBitmap(qrBitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}
