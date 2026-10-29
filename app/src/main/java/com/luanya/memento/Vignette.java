package com.luanya.memento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Vignette {
    private int id;
    private String title;
    private String date;
    private String location;
    private String weather;
    private int temp;
    private String journalEntry;
    private boolean isFavorite;
    private int mood;
    private String imagePath;

    // Constructor
    public Vignette (int id, String ttl, String dt, String loc,
                    String wthr, int tmp, String jrnl,
                    boolean fave, int md, String img) {
        this.id = id;
        title = ttl;
        date = dt;
        location = loc;
        weather = wthr;
        temp = tmp;
        journalEntry = jrnl;
        isFavorite = fave;
        mood = md;
        imagePath = img;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getFormattedDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return ldt.format(dtf);
    }

    public String getLocation() {
        return location;
    }

    public String getWeather() {
        return weather;
    }

    public int getTemp() {
        return temp;
    }

    public String getJournalEntry() {
        return journalEntry;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public int getMood() {
        return mood;
    }

    public String getImagePath() {
        return imagePath;
    }
}
