package com.example.explorenow;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorenow.adapter.LandmarkAdapter;
import com.example.explorenow.viewmodel.LandmarkViewModel;

public class LandmarkListActivity extends AppCompatActivity {

    private LandmarkViewModel viewModel;
    private LandmarkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLandmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LandmarkAdapter(this, l -> {
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(LandmarkViewModel.class);
        viewModel.getAll().observe(this, adapter::setLandmarks);
    }
}
