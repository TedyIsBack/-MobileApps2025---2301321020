package com.example.explorenow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class LandmarkListActivity extends AppCompatActivity {

    private LandmarkViewModel viewModel;
    private LandmarkAdapter adapter;
    private LinearLayout emptyStateLayout;
    private TextView tvEmpty;
    private MaterialButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLandmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyStateLayout = findViewById(R.id.emptyStateLayout);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnAdd = findViewById(R.id.btnAddLandmark);

        adapter = new LandmarkAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(LandmarkViewModel.class);

        // Добавяне на landmark от празно състояние
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, EditorActivity.class));
        });

        // Клик върху landmark за редакция
        adapter.setOnItemClick(landmark -> {
            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra(EditorActivity.EXTRA_LANDMARK_ID, landmark.id);
            startActivity(intent);
        });

        // QR бутон
        adapter.setOnQrClick(landmark -> {
            String data = LCardUtils.landmarkToLCard(landmark);
            LandmarkQrActivity.start(this, data);
        });

        // Delete бутон
        adapter.setOnDeleteClick(landmark -> {
            viewModel.delete(landmark);
            Snackbar.make(recyclerView, "Deleted: " + landmark.name, Snackbar.LENGTH_SHORT).show();
        });

        // Наблюдение на списъка с landmark-и
        viewModel.getAllLandmarks().observe(this, landmarks -> {
            if (landmarks == null || landmarks.isEmpty()) {
                emptyStateLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyStateLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.submitList(landmarks);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAddLandmark);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditorActivity.class);
            startActivity(intent);
        });

    }
}
