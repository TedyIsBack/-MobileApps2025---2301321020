package com.example.explorenow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorenow.adapter.LandmarkAdapter;
import com.example.explorenow.viewmodel.LandmarkViewModel;
import com.google.android.material.button.MaterialButton;

public class LandmarkListActivity extends AppCompatActivity {

    public static final String LANDMARK_ID = "landmark_id";
    private LandmarkViewModel viewModel;
    private LandmarkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLandmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView tvEmpty = findViewById(R.id.tvEmpty);
        MaterialButton btnAdd = findViewById(R.id.btnAddLandmark);

        adapter = new LandmarkAdapter();
        recyclerView.setAdapter(adapter);

//        adapter.setOnItemClick(landmark -> {
//            Intent intent = new Intent(this, LandmarkDetailActivity.class);
//            intent.putExtra(LANDMARK_ID, landmark.id);
//            startActivity(intent);
//        });

        adapter.setOnQrClick(landmark -> {
            Intent intent = new Intent(this, LandmarkQrActivity.class);
            intent.putExtra(LANDMARK_ID, landmark.id);
            startActivity(intent);
        });



        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditorActivity.class);
            startActivity(intent);
        });

        viewModel = new ViewModelProvider(this).get(LandmarkViewModel.class);
        viewModel.getAllLandmarks().observe(this, landmarks -> {
                    if (landmarks == null || landmarks.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        btnAdd.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.GONE);
                        btnAdd.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.submitList(landmarks);
                    }
                });

        adapter.setOnQrClick(landmark -> {
            Toast.makeText(this, "Generate QR for " + landmark.name, Toast.LENGTH_SHORT).show();
        });

        viewModel.getAllLandmarks().observe(this, landmarks -> adapter.submitList(landmarks));
    }
}
