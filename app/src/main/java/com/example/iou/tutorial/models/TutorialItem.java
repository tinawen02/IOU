package com.example.iou.tutorial.models;

public class TutorialItem {

    private String title;
    private String description;
    private int image;

    public TutorialItem(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getScreenImg() {
        return image;
    }
}