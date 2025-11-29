package com.example.explorenow.utils;

import com.example.explorenow.data.Landmark;

public class LCardUtils {

    public static final String NAME = "NAME:";
    public static final String DESC = "DESC:";
    public static final String ADDRESS = "ADDR:";
    public static final String PHOTO = "PHOTO:";
    public static final String END_LABEL = "END:LANDMARK";
    public static final String BEGIN_LABEL = "BEGIN:LANDMARK";
    public static final String VERSION = "VERSION:1.0";

    public static String landmarkToLCard(Landmark l) {
        StringBuilder sb = new StringBuilder();

        sb.append(BEGIN_LABEL);
        sb.append(VERSION);

        sb.append(NAME).append(l.name).append("\n");

        if (l.description != null && !l.description.isEmpty()) {
            sb.append(DESC).append(l.description).append("\n");
        }

        if (l.address != null && !l.address.isEmpty()) {
            sb.append(ADDRESS).append(l.address).append("\n");
        }

        if (l.photoUri != null && !l.photoUri.isEmpty()) {
            sb.append(PHOTO).append(l.photoUri).append("\n");
        }

        sb.append(END_LABEL);

        return sb.toString();
    }

    public static Landmark lCardToLandmark(String lcardRaw) {

    return null;
    }
}
