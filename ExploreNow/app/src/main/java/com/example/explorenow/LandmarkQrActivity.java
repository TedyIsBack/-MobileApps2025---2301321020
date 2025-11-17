package com.example.explorenow;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.explorenow.data.Landmark;
import com.example.explorenow.repository.AppDatabase;
import com.example.explorenow.utils.LCardUtils;
import com.example.explorenow.utils.QrUtils;
import com.google.zxing.WriterException;

public class LandmarkQrActivity extends AppCompatActivity {

    public static final String QR_TEXT = "QR Code for: ";
    public static final String LANDMARK_ID = "landmark_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_qr);

        TextView tv = findViewById(R.id.tvTitle);
        ImageView img = findViewById(R.id.imgQr);

        int landmarkId = getIntent().getIntExtra(LANDMARK_ID, -1);
        if (landmarkId == -1) {
            finish();
            return;
        }
        var dao = AppDatabase.getInstance(getApplicationContext()).landmarkDao();
        dao.getById(landmarkId).observe(this, landmark -> {
            if (landmark == null) return;

            tv.setText(QR_TEXT + landmark.name);

            String vcard = LCardUtils.landmarkToLCard(landmark);

            try {
                Bitmap qr = QrUtils.generateQrBitmap(vcard);
                img.setImageBitmap(qr);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
    }
}
