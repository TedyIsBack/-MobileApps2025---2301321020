package com.example.explorenow.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.explorenow.data.Landmark;
import com.example.explorenow.repository.LandmarkRepository;

import java.util.List;

public class LandmarkViewModel extends AndroidViewModel {
    private LandmarkRepository repo;
    private LiveData<List<Landmark>> all;
    public LandmarkViewModel(@NonNull Application application) {
        super(application);
        repo = new LandmarkRepository(application);
        all = repo.getAllLandmarks();
    }
    public LiveData<List<Landmark>> getAll() { return all; }
    public void insert(Landmark l) { repo.insert(l); }
    public void update(Landmark l) { repo.update(l); }
    public void delete(Landmark l) { repo.delete(l); }
    public LiveData<Landmark> getById(int id) { return repo.getById(id); }
}

