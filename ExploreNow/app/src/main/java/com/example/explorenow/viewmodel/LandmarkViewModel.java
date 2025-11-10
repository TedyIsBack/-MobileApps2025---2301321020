package com.example.explorenow.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.explorenow.data.Landmark;
import com.example.explorenow.repository.LandmarkRepository;

import java.util.List;

public class LandmarkViewModel extends AndroidViewModel {

    private final LandmarkRepository repository;
    private final LiveData<List<Landmark>> allLandmarks;

    public LandmarkViewModel(@NonNull Application application) {
        super(application);
        repository = new LandmarkRepository(application);
        allLandmarks = repository.getAllLandmarks();
    }

    public LiveData<List<Landmark>> getAllLandmarks() {
        return allLandmarks;
    }

    public void insert(Landmark landmark) {
        repository.insert(landmark);
    }

    public void update(Landmark landmark) {
        repository.update(landmark);
    }

    public void delete(Landmark landmark) {
        repository.delete(landmark);
    }

    public LiveData<Landmark> getById(int id) {
        return repository.getById(id);
    }
}
