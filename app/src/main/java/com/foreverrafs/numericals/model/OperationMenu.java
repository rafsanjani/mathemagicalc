package com.foreverrafs.numericals.model;

/**
 * Created by Rafsanjani on 6/5/2019
 */
public class OperationMenu {
    private String title;
    private int imageResource;

    public OperationMenu(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }
}