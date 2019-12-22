package com.example.listviewandalertdialogwithsql;

public class Landmarks {
    private String Landmarks;
    private String ID;
    private String preferences;

    public Landmarks(String landmarks, String ID, String preferences) {
        Landmarks = landmarks;
        this.ID = ID;
        this.preferences = preferences;
    }

    public String getLandmarks() {
        return Landmarks;
    }

    public void setLandmarks(String landmarks) {
        Landmarks = landmarks;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
}
