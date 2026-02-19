package com.example.moodsmells;

public class Memory {
    private String date;
    private String name;
    private String typeOfSmell;
    private String mood;

    private String photo;

    public Memory() {
        // مطلوب من Firebase
    }

    public Memory(String date, String name, String typeOfSmell, String mood) {
        this.date = date;
        this.name = name;
        this.typeOfSmell = typeOfSmell;
        this.mood = mood;
        this.photo = photo;
    }

    // Getters and Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTypeOfSmell() { return typeOfSmell; }
    public void setTypeOfSmell(String typeOfSmell) { this.typeOfSmell = typeOfSmell; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
