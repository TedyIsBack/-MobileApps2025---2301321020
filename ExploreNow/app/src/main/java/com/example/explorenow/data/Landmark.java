package com.example.explorenow.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "landmarks")
public class Landmark {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    public String name;
    public String description;
    public String address;
    public String photoUri;
    public Landmark(@NonNull String name, String description, String address, String photoUri) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.photoUri = photoUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Landmark)) return false;
        Landmark l = (Landmark) o;
        return id == l.id &&
                Objects.equals(name, l.name) &&
                Objects.equals(description, l.description) &&
                Objects.equals(address, l.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address);
    }
}
