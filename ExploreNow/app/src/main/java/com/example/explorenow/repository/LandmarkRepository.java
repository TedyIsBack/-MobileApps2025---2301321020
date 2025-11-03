package com.example.explorenow.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.explorenow.data.Landmark;
import com.example.explorenow.data.LandmarkDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LandmarkRepository {
    private LandmarkDao dao;
    private LiveData<List<Landmark>> allLandmarks;
    private Executor executor = Executors.newSingleThreadExecutor();
    public LandmarkRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        dao = db.landmarkDao();
        allLandmarks = dao.getAll();
    }

    public LiveData<List<Landmark>> getAllLandmarks() { return allLandmarks; }
    public void insert(final Landmark l) {
        executor.execute(() -> dao.insert(l));
    }

    public void update(final Landmark l) {
        executor.execute(() -> dao.update(l));
    }

    public void delete(final Landmark l) {
        executor.execute(() -> dao.delete(l));
    }

    public LiveData<Landmark> getById(int id) {
        return dao.getById(id);
    }
}
