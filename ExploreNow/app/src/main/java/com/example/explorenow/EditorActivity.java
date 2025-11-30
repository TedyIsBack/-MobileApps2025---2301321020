package com.example.explorenow;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.explorenow.data.Landmark;
import com.example.explorenow.repository.PhotoRepository;
import com.example.explorenow.viewmodel.LandmarkViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.IOException;

public class EditorActivity extends BaseActivity {

    public static final String EXTRA_LANDMARK_ID = "landmark_id";
    private PhotoRepository photoRepo;

    private EditText etName, etDesc, etAddress;
    private ImageView imgPreview;
    private MaterialButton btnBack, btnUpload, btnSave;

    private LandmarkViewModel viewModel;
    private Landmark currentLandmark;
    private Uri imageUri;

    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        photoRepo = new PhotoRepository(this);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDescription);
        etAddress = findViewById(R.id.etAddress);
        imgPreview = findViewById(R.id.imgPreview);
        btnBack = findViewById(R.id.btnBack);
        btnUpload = findViewById(R.id.btnUploadImage);
        btnSave = findViewById(R.id.btnSave);

        viewModel = new ViewModelProvider(this).get(LandmarkViewModel.class);

        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Image picker
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Uri selectedUri = result.getData().getData();
                        try {
                            String savedPath = photoRepo.saveFromUri(selectedUri); // запазва локално
                            imageUri = Uri.fromFile(new File(savedPath));
                            imgPreview.setImageURI(imageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        // Save button
        btnSave.setOnClickListener(this::saveLandmark);

        // Check if editing existing landmark
        int landmarkId = getIntent().getIntExtra(EXTRA_LANDMARK_ID, -1);
        if (landmarkId != -1) {
            viewModel.getById(landmarkId).observe(this, lm -> {
                if (lm != null) {
                    currentLandmark = lm;
                    populateUI(lm);
                }
            });
        }
    }

    private void populateUI(Landmark lm) {
        etName.setText(lm.name);
        etDesc.setText(lm.description);
        etAddress.setText(lm.address);
        if (lm.photoUri != null) {
            imageUri = Uri.parse(lm.photoUri);
            imgPreview.setImageURI(imageUri);
        }
    }

    private void saveLandmark(View v) {
        String name = etName.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String adr = etAddress.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Please enter a name");
            etName.requestFocus();
            return;
        }

        if (adr.isEmpty()) {
            etAddress.setError("Please enter an address");
            etAddress.requestFocus();
            return;
        }

        if (currentLandmark != null) {
            // Update existing
            currentLandmark.name = name;
            currentLandmark.description = desc;
            currentLandmark.address = adr;
            currentLandmark.photoUri = imageUri != null ? imageUri.toString() : null;
            viewModel.update(currentLandmark);
            Toast.makeText(this, "Landmark updated!", Toast.LENGTH_SHORT).show();
        } else {
            // Insert new
            Landmark l = new Landmark(name, desc, adr,
                    imageUri != null ? imageUri.toString() : null);
            viewModel.insert(l);
            Toast.makeText(this, "Landmark added!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
