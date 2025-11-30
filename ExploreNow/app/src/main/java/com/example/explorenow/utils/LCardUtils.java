package com.example.explorenow.utils;

import com.example.explorenow.data.Landmark;

public class LCardUtils {

    // Старите константи за LCard може да останат за другите функции
    public static final String ID = "ID:";
    public static final String NAME = "NAME:";
    public static final String DESC = "DESC:";
    public static final String ADDRESS = "ADDR:";
    public static final String PHOTO = "PHOTO:";
    public static final String END_LABEL = "END:LANDMARK";
    public static final String BEGIN_LABEL = "BEGIN:LANDMARK\n";
    public static final String VERSION = "VERSION:1.0\n";

    // Нова функция за custom URL scheme
    public static String landmarkToQrUrl(Landmark l) {
        return "explorenow://landmark?id=" + l.id;
    }

    // Старият LCard метод може да остане, ако искаш
    public static String landmarkToLCard(Landmark l) {
        StringBuilder sb = new StringBuilder();
        sb.append(BEGIN_LABEL);
        sb.append(VERSION);
        sb.append(ID).append(l.id).append("\n");
        sb.append(NAME).append(l.name).append("\n");
        sb.append(DESC).append(l.description).append("\n");
        sb.append(ADDRESS).append(l.address).append("\n");
        if (l.photoUri != null) {
            sb.append(PHOTO).append(l.photoUri).append("\n");
        }
        sb.append(END_LABEL);
        return sb.toString();
    }
}
