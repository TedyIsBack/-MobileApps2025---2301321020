package com.example.explorenow.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LandmarkDao {
    @Query("SELECT * FROM landmarks ORDER BY id DESC")
    LiveData<List<Landmark>> getAll();
    @Query("SELECT * FROM landmarks WHERE id = :id")
    LiveData<Landmark> getById(int id);
    @Insert
    void insert(Landmark landmark);
    @Update
    void update(Landmark landmark);
    @Delete
    void delete(Landmark landmark);
}
