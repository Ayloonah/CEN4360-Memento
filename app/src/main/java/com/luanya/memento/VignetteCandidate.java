package com.luanya.memento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class VignetteCandidate {
    private int id;
    private String location;
    private String date;
    private String weather;
    private int temp;
    private String reason;

    // constructor
    public VignetteCandidate (int id, String loc, String dt,
                              String wthr, int tmp, String rsn) {
        this.id = id;
        location = loc;
        date = dt;
        weather = wthr;
        temp = tmp;
        reason = rsn;
    }

    // getter methods
    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getFormattedDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return ldt.format(dtf);
    }

    // time for the card title like 4:15 PM
    public String getFormattedTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
        LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return ldt.format(dtf);
    }

    public String getWeather() {
        return weather;
    }

    public int getTemp() {
        return temp;
    }

    public String getReason() {
        return reason;
    }
}