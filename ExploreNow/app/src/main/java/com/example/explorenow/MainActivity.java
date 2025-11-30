package com.example.explorenow;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        Button btnViewAll = findViewById(R.id.btnViewAll);
        Button btnAddNew = findViewById(R.id.btnAddNew);

        btnViewAll.setOnClickListener(v ->
                startActivity(new Intent(this, LandmarkListActivity.class))
        );

        btnAddNew.setOnClickListener(v ->
                startActivity(new Intent(this, EditorActivity.class))
        );
    }
}
