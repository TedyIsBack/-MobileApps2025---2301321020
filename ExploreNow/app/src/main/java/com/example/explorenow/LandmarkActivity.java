package com.example.explorenow;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.explorenow.data.Landmark;
import com.example.explorenow.repository.AppDatabase;
import com.example.explorenow.utils.LCardUtils;
import com.google.android.material.button.MaterialButton;

public class LandmarkActivity extends AppCompatActivity {

    private ImageView imgLandmark;
    private TextView tvName, tvAddress, tvDesc;
    private MaterialButton btnQr, btnDelete;

    private Landmark landmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);

        imgLandmark = findViewById(R.id.imgLandmark);
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvDesc = findViewById(R.id.tvDescription);
        btnQr = findViewById(R.id.btnQr);
        btnDelete = findViewById(R.id.btnDelete);

        int landmarkId = getIntent().getIntExtra("landmark_id", -1);
        if (landmarkId != -1) {
            AppDatabase.getInstance(this)
                    .landmarkDao()
                    .getById(landmarkId)
                    .observe(this, new Observer<Landmark>() {
                        @Override
                        public void onChanged(Landmark lm) {
                            if (lm != null) {
                                landmark = lm;
                                populateUI();
                            } else {
                                Toast.makeText(LandmarkActivity.this, "Landmark not found", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
        }

        btnQr.setOnClickListener(v -> {
            if (landmark != null) {
                String data = LCardUtils.landmarkToLCard(landmark);
                LandmarkQrActivity.start(this, data);
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (landmark != null) {
                new AlertDialog.Builder(this)
                        .setTitle("Delete Landmark")
                        .setMessage("Are you sure you want to delete this landmark?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            AppDatabase.getInstance(getApplicationContext())
                                    .landmarkDao()
                                    .delete(landmark);
                            Toast.makeText(this, "Landmark deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    private void populateUI() {
        if (landmark.photoUri != null) {
            imgLandmark.setImageURI(Uri.parse(landmark.photoUri));
        } else {
            imgLandmark.setImageResource(R.drawable.ic_launcher_background);
        }

        tvName.setText(landmark.name);
        tvAddress.setText(landmark.address);
        tvDesc.setText(landmark.description);
    }
}
