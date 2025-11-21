package com.example.explorenow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorenow.adapter.LandmarkAdapter;
import com.example.explorenow.viewmodel.LandmarkViewModel;
import com.google.android.material.button.MaterialButton;

public class LandmarkListActivity extends AppCompatActivity {

    private LandmarkViewModel viewModel;
    private LandmarkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLandmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LandmarkAdapter();
        recyclerView.setAdapter(adapter);

        TextView tvEmpty = findViewById(R.id.tvEmpty);
        MaterialButton btnAdd = findViewById(R.id.btnAddLandmark);

        adapter.setOnQrClick(landmark -> {
            Intent intent = new Intent(this, LandmarkQrActivity.class);
            intent.putExtra(LandmarkQrActivity.LANDMARK_ID, landmark.id);
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
    }
}
