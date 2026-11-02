package com.luanya.memento;

public class Album {
    private int id;
    private String name;
    private String createdAt;
    private boolean isFavorite;
    private String color;

    // constructor
    public Album (int id, String nm, String crtd,
                  boolean fave, String clr) {
        this.id = id;
        name = nm;
        createdAt = crtd;
        isFavorite = fave;
        color = clr;
    }

    // getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public String getColor() {
        return color;
    }

    // setter methods
    public void setIsFavorite(boolean fave) {
        isFavorite = fave;
    }
}