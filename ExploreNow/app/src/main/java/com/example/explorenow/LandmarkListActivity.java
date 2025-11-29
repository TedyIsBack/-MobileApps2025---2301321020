package com.example.explorenow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorenow.adapter.LandmarkAdapter;
import com.example.explorenow.data.Landmark;
import com.example.explorenow.utils.LCardUtils;
import com.example.explorenow.viewmodel.LandmarkViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class LandmarkListActivity extends AppCompatActivity {
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

        viewModel = new ViewModelProvider(this).get(LandmarkViewModel.class);

        adapter.setOnQrClick(landmark -> {
            // Генерираме LCard string и го пращаме
            String lcardData = LCardUtils.landmarkToLCard(landmark);
            LandmarkQrActivity.start(this, lcardData);
        });

        adapter.setOnEditClick(landmark -> {
            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra(EditorActivity.EXTRA_LANDMARK_ID, landmark.id);
            startActivity(intent);
        });

        adapter.setOnDeleteClick(landmark -> {
            viewModel.delete(landmark);
            Snackbar.make(recyclerView, "Deleted: " + landmark.name, Snackbar.LENGTH_SHORT).show();
        });

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditorActivity.class);
            startActivity(intent);
        });

        viewModel.getAllLandmarks().observe(this, landmarks -> {
            if (landmarks == null || landmarks.isEmpty()) {
                tvEmpty.setVisibility(TextView.VISIBLE);
                btnAdd.setVisibility(MaterialButton.VISIBLE);
                recyclerView.setVisibility(RecyclerView.GONE);
            } else {
                tvEmpty.setVisibility(TextView.GONE);
                btnAdd.setVisibility(MaterialButton.GONE);
                recyclerView.setVisibility(RecyclerView.VISIBLE);
                adapter.submitList(landmarks);
            }
        });
    }
}
