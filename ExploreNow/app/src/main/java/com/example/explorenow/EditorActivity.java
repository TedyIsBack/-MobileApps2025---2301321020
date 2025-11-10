package com.example.explorenow;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.explorenow.R;
import com.example.explorenow.data.Landmark;
import com.example.explorenow.viewmodel.LandmarkViewModel;
import com.google.android.material.button.MaterialButton;

public class EditorActivity extends AppCompatActivity {

    private EditText etName, etDesc, etAddress;
    private ImageView imgPreview;
    private MaterialButton btnBack;
    private LandmarkViewModel viewModel;
    private static final int PICK_IMAGE = 101;
    private Uri imageUri;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDescription);
        etAddress = findViewById(R.id.etAddress);
        btnBack = findViewById(R.id.btnBack);
        imgPreview = findViewById(R.id.imgPreview);
        viewModel = new ViewModelProvider(this).get(LandmarkViewModel.class);

        btnBack.setOnClickListener(v -> finish());

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        imageUri = result.getData().getData();
                        imgPreview.setImageURI(imageUri);
                    }
                }
        );

        MaterialButton btnUpload = findViewById(R.id.btnUploadImage);
        btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        MaterialButton btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this::saveLandmark);
    }


    private void saveLandmark(View v) {
        String name = etName.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String adr = etAddress.getText().toString();

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

        Landmark l = new Landmark(name, desc, adr,
                imageUri != null ? imageUri.toString() : null);
        viewModel.insert(l);
        Toast.makeText(this, "Successfully added!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgPreview.setImageURI(imageUri);
        }
    }
}
